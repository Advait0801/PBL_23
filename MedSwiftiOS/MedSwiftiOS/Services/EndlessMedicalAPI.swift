//
//  EndlessMedicalAPI.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 08/03/23.
//

import Foundation

class SymptomAnalyser {
    
    var symptoms: [Symptom]
    var diseases: [String : Disease]
    
    let baseURL = "https://api.endlessmedical.com/v1/dx/"
    
    func getSessionID() async throws -> String {
        guard let url = URL(string: baseURL + "InitSession") else { throw SymptomAnalyzerError.invalidURL }
        let (data, _) = try await URLSession.shared.data(from: url)
        guard let decodedData = try JSONSerialization
            .jsonObject(with: data, options: .mutableContainers) as? [String : String] else { throw SymptomAnalyzerError.badResponse }
        if decodedData["status"] == "ok" {
            guard let sessionID = decodedData["SessionID"] else { throw SymptomAnalyzerError.badResponse }
            return sessionID
        } else {
            throw SymptomAnalyzerError.badResponse
        }
        
    }
    
    func acceptTermsOfUse(sessionID: String) async throws {
        let passphrase = "I have read, understood and I accept and agree to comply with the Terms of Use of EndlessMedicalAPI and Endless Medical services. The Terms of Use are available on endlessmedical.com"
        guard var url = URL(string: baseURL + "AcceptTermsOfUse") else { throw SymptomAnalyzerError.invalidURL }
        let queryItems = [
            URLQueryItem(name: "SessionID", value: sessionID),
            URLQueryItem(name: "passphrase", value: passphrase),
        ]
        
        url.append(queryItems: queryItems)
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        
        guard let (data, _) = try? await URLSession.shared.upload(for: request, from: Data()) else { throw SymptomAnalyzerError.termsNotAccepted }
        guard let decodedData = try? JSONSerialization
            .jsonObject(with: data, options: .mutableContainers) as? [String : String] else { throw SymptomAnalyzerError.badResponse }
        if decodedData["status"] != "ok" {
            throw SymptomAnalyzerError.badResponse
        }
    }
    
    func uploadSymptom(sessionID: String, symptomName: String, value: String) async throws {
        let queryItems = [
            URLQueryItem(name: "SessionID", value: sessionID),
            URLQueryItem(name: "name", value: symptomName),
            URLQueryItem(name: "value", value: value)
        ]
        guard var url = URL(string: baseURL + "UpdateFeature") else { throw SymptomAnalyzerError.invalidURL }
        url.append(queryItems: queryItems)
        
        url.append(queryItems: queryItems)
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        
        guard let (data, _) = try? await URLSession.shared.upload(for: request, from: Data()) else { throw SymptomAnalyzerError.featureNotUpdated }
        guard let decodedData = try JSONSerialization
            .jsonObject(with: data, options: .mutableContainers) as? [String : String] else { throw SymptomAnalyzerError.badResponse }
        if decodedData["status"] != "ok" {
            throw SymptomAnalyzerError.featureNotUpdated
        }
    }
    
    func analyze(sessionID: String) async throws -> [DiseaseProbability] {
        let queryItems = [
            URLQueryItem(name: "SessionID", value: sessionID),
        ]
        guard var url = URL(string: baseURL + "Analyze") else { throw SymptomAnalyzerError.invalidURL }
        url.append(queryItems: queryItems)
        let (data, _) = try await URLSession.shared.data(from: url)
        guard let reponseAnalysis = try? JSONDecoder().decode(AnalysisResponse.self, from: data) else { throw SymptomAnalyzerError.analysisFailed }
        if reponseAnalysis.status != "ok" {
            throw SymptomAnalyzerError.analysisFailed
        } else {
            var diagnosis = [DiseaseProbability]()
            for pair in reponseAnalysis.diseases {
                guard let disease = self.diseases[pair.keys.first!] else { continue }
                diagnosis.append(DiseaseProbability(disease: disease, probability: Double(pair.values.first!)!))
                
            }
            return diagnosis
        }
    }
    
    func suggestedQuestions(sessionID: String) async throws -> [Symptom] {
        let queryItems = [
            URLQueryItem(name: "SessionID", value: sessionID),
        ]
        guard var url = URL(string: baseURL + "GetSuggestedFeatures_PatientProvided") else { throw SymptomAnalyzerError.invalidURL }
        url.append(queryItems: queryItems)
        let (data, _) = try await URLSession.shared.data(from: url)
        guard let response = try? JSONDecoder().decode(SuggestedQuestionsResponse.self, from: data) else { throw SymptomAnalyzerError.analysisFailed }
        if response.status == "ok" {
            let suggestedFeatureNames = response.suggestedFeatures.map { $0[0] }
            let questionObjects = self.symptoms.filter { smptm in suggestedFeatureNames.contains { strng in
                    smptm.name == strng
                }
            }
            return questionObjects
        } else {
            throw SymptomAnalyzerError.analysisFailed
        }
    }
    
    func getDiagnosis(symptoms: [Symptom : Double], incrementer: @escaping () -> ()) async -> ([DiseaseProbability], [Symptom]) {
        do {
            let sessionID = try await getSessionID()
            DispatchQueue.main.async {
                incrementer()
            }
            try await acceptTermsOfUse(sessionID: sessionID)
            DispatchQueue.main.async {
                incrementer()
            }
            try await withThrowingTaskGroup(of: Void.self) { group in
                for pair in symptoms {
                    let value = String(format: pair.key.type == .double ? "%.2f" : "%.0f", pair.value)
                    group.addTask {
                        return try await self.uploadSymptom(sessionID: sessionID, symptomName: pair.key.name, value: value)
                    }
                }
                for try await _ in group {
                    DispatchQueue.main.async {
                        incrementer()
                    }
                }
            }
            let analysis = try await analyze(sessionID: sessionID)
            DispatchQueue.main.async {
                incrementer()
            }
            let suggestedQuestions = try await suggestedQuestions(sessionID: sessionID)
            return (analysis, suggestedQuestions)
        } catch {
            print(error)
            return ([], [])
        }
    }
    
    init() {
        let symptomsData: Data
        let diseasesData: Data
        let symptomsFilename = "SymptomsOutput.json"
        let diseasesFilename = "DiseasesOutput.json"
        
        guard let symptomsFile = Bundle.main.url(forResource: symptomsFilename, withExtension: nil) else {
            fatalError("Couldn't find \(symptomsFilename) in main bundle.")
        }
        guard let diseasesFile = Bundle.main.url(forResource: diseasesFilename, withExtension: nil) else {
            fatalError("Couldn't find \(diseasesFilename) in main bundle.")
        }
        
        do {
            symptomsData = try Data(contentsOf: symptomsFile)
            diseasesData = try Data(contentsOf: diseasesFile)
        } catch {
            fatalError("Couldn't load data from main bundle:\n\(error)")
        }
        
        do {
            let decoder = JSONDecoder()
            self.symptoms = try decoder.decode(Symptoms.self, from: symptomsData)
            let diseasesArray = try decoder.decode(Diseases.self, from: diseasesData)
            self.diseases = [String : Disease]()
            diseasesArray.forEach { disease in
                self.diseases[disease.text] = disease
            }
            
        } catch {
            fatalError("Couldn't parse \(symptomsFilename) as \(Symptoms.self): \n\(error)")
        }
        
        
    }
}

enum SymptomAnalyzerError: Error, CustomStringConvertible {
    case invalidURL
    case badResponse
    case termsNotAccepted
    case featureNotUpdated
    case analysisFailed
    
    var description: String {
        switch self {
            case .invalidURL: return "invalidURL"
            case .badResponse: return "badResponse"
            case .termsNotAccepted: return "termsNotAccepted"
            case .featureNotUpdated: return "featureNotUpdated"
            case .analysisFailed: return "analysisFailed"
        }
    }
}
