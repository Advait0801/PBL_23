//
//  SymptomAnalyzerViewModel.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 10/03/23.
//

import SwiftUI
import CoreLocation

class SymptomAnalyzerViewModel: ObservableObject {

    @Published var selectedSymptoms = [Symptom : Double]()
    @Published var analysis = [DiseaseProbability]()
    @Published var suggestedSymptoms = [Symptom]()
    @Published var progressSteps = 0
    @Published var isLoading = false
    @Published var history = [DiagnosisHistory]()
    
    let manager = FirebaseManager.shared
    let api = SymptomAnalyser()
    let user: UserEntity
    let clManager = CLLocationManager()
    
    var totalSteps: Int {
        selectedSymptoms.count + 3
    }
    
    func setSymptom(_ symptom: Symptom, value: Double) {
        selectedSymptoms[symptom] = value
    }
    
    func deleteSymptom(_ symptom: Symptom) {
        selectedSymptoms.removeValue(forKey: symptom)
    }
    
    func getAnalysis() async {
        DispatchQueue.main.async {
            self.progressSteps = 0
            self.isLoading = true
            self.analysis = []
        }
        let (analysis, fetchedSymptoms) = await api.getDiagnosis(symptoms: selectedSymptoms) {
            DispatchQueue.main.async {
                withAnimation(.easeOut(duration: 1.0)) {
                    self.progressSteps += 1
                }
            }
        }
        DispatchQueue.main.async {
            self.analysis = analysis
            self.isLoading = false
            self.suggestedSymptoms = []
            for symptom in fetchedSymptoms {
                withAnimation {
                    self.suggestedSymptoms.append(symptom)
                }
            }
        }
    }
    
    func saveDiagnosis() async {
        var allSymptoms = [[String : Any]]()
        var allDiagnoses = [[String : Any]]()
        
        for symptom in selectedSymptoms {
            allSymptoms.append(["name" : symptom.key.name, "value" : symptom.value])
        }
        
        for diagnosis in analysis {
            allDiagnoses.append(["name" : diagnosis.disease.name, "value" : diagnosis.probability])
        }
        
        let loc = clManager.location?.coordinate ?? CLLocationCoordinate2D.init()
        
        let diagnosisData: [String : Any] = ["time" : Date(), "lat" : loc.latitude, "lon" : loc.longitude]
        
        do {
            let docID = try await manager.db
                .collection("users")
                .document(user.id ?? "test")
                .collection("diagnoses")
                .addDocument(data: diagnosisData)
                .documentID
            
            for symptom in allSymptoms {
                let _ = try await manager.db
                    .collection("users")
                    .document(user.id ?? "test")
                    .collection("diagnoses")
                    .document(docID)
                    .collection("symptoms")
                    .addDocument(data: symptom)
            }
            
            for diagnosis in allDiagnoses {
                let _ = try await manager.db
                    .collection("users")
                    .document(user.id ?? "test")
                    .collection("diagnoses")
                    .document(docID)
                    .collection("predictions")
                    .addDocument(data: diagnosis)
            }
            
        } catch {
            print(error.localizedDescription)
        }
    }
    
    func getHistory() async {
        
        guard let uid = user.id else { return }
        if !self.history.isEmpty { return }
        do {
            let diagnosisCollectionRef = manager.db
                .collection("users")
                .document(uid)
                .collection("diagnoses")
            
            let diagnosisCollection = try await diagnosisCollectionRef.getDocuments()
            try await withThrowingTaskGroup(of: DiagnosisHistory.self) { group in
                for diagnosisDoc in diagnosisCollection.documents {
                    group.addTask {
                        let docID = diagnosisDoc.documentID
                        var historyItem = DiagnosisHistory(diagnosisDoc.data())
                        
                        async let predictions = try diagnosisCollectionRef
                            .document(docID)
                            .collection("predictions")
                            .getDocuments()
                            .documents
                        
                        
                        async let symptoms = try diagnosisCollectionRef
                            .document(docID)
                            .collection("symptoms")
                            .getDocuments()
                            .documents
                        
                        let pred = try await predictions
                        let symp = try await symptoms
                        
                        print(pred.count, "predictions recieved")
                        
                        for symptomSnapshot in symp {
                            let symptomData = symptomSnapshot.data()
                            guard let symptomObj = self.api.symptoms.first(where: { s in
                                s.name == symptomData["name"] as? String
                            }) else { continue }
                            historyItem.symptoms[symptomObj] = symptomData["value"] as? Double
                        }
                        for predictionSnapshot in pred {
                            let predictionData = predictionSnapshot.data()
                            guard let name = predictionData["name"] as? String else { continue }
                            print("got name")
                            guard let disease = self.api.diseases.values.first(where: { $0.name == name }) else { continue }
                            print("got disease")
                            guard let value = predictionData["value"] as? Double else { continue }
                            print("got value")
                            let predObj = DiseaseProbability(disease: disease, probability: value * 100)
                            historyItem.predictions.append(predObj)
                        }
                        
                        return historyItem
                    }
                }
                for try await historyItem in group {
                    DispatchQueue.main.async {
                        self.history.append(historyItem)
                        print(historyItem)
                    }
                }
            }
        } catch {
            print(error.localizedDescription)
        }
    }
    
    func requestCLAuthorization() {
        if clManager.location == nil {
            clManager.requestWhenInUseAuthorization()
        }
    }
    
    init(_ userEntity: UserEntity) {
        print("INIT")
        let age = Int((-userEntity.dateOfBirth.timeIntervalSinceNow) / (3600 * 24 * 365.25))
        let bmi = userEntity.weight * 10000 / (userEntity.height * userEntity.height)
        let gender = api.symptoms.first { $0.name == "Gender" }!
        self.user = userEntity
        
        selectedSymptoms[api.symptoms[1]] = bmi
        selectedSymptoms[api.symptoms[0]] = Double(age)
        if userEntity.gender == .male {
            selectedSymptoms[gender] = 2.0
        } else if userEntity.gender == .female {
            selectedSymptoms[gender] = 3.0
        } else {
            selectedSymptoms[gender] = 1.0
        }
    }
    
}
