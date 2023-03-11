//
//  EndlessMedicalAPI.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 08/03/23.
//

import Foundation

class SymptomAnalyser {
    
    var symptoms: [Symptom]
    
    init() {
        let data: Data
        let filename = "SymptomsOutput.json"
        guard let file = Bundle.main.url(forResource: filename, withExtension: nil) else {
            fatalError("Couldn't find \(filename) in main bundle.")
        }
        
        do {
            data = try Data(contentsOf: file)
        } catch {
            fatalError("Couldn't load \(filename) from main bundle:\n\(error)")
        }
        
        do {
            let decoder = JSONDecoder()
            self.symptoms = try decoder.decode(Symptoms.self, from: data)
        } catch {
            fatalError("Couldn't parse \(filename) as \(Symptoms.self): \n\(error)")
        }
    }
}
