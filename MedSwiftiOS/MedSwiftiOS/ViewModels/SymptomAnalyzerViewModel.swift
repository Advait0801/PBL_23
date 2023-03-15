//
//  SymptomAnalyzerViewModel.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 10/03/23.
//

import Foundation

class SymptomAnalyzerViewModel: ObservableObject {
    let api = SymptomAnalyser()
    @Published var selectedSymptoms = [Symptom : Double]()
    
    func setSymptom(_ symptom: Symptom, value: Double) {
        selectedSymptoms[symptom] = value
    }
    
    func deleteSymptom(_ symptom: Symptom) {
        selectedSymptoms.removeValue(forKey: symptom)
    }
    
    init(_ userEntity: UserEntity) {
        
        let age = Int((-userEntity.dateOfBirth.timeIntervalSinceNow) / (3600 * 24 * 365.25))
        let bmi = userEntity.weight * 10000 / (userEntity.height * userEntity.height)
        let gender = api.symptoms.first { $0.name == "Gender" }!
        
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
