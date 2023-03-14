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
        
        selectedSymptoms[api.symptoms[1]] = bmi
        selectedSymptoms[api.symptoms[0]] = Double(age)
        
    }
}
