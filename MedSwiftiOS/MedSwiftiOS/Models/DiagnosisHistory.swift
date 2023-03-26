//
//  DiagnosisHistory.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 25/03/23.
//

import Foundation
import Firebase

struct DiagnosisHistory: Codable, Hashable {
    let lat: Double
    let lon: Double
    let time: Date?
    var symptoms: [Symptom : Double]
    var predictions: [DiseaseProbability]
    
    init(_ data: [String : Any]) {
        self.lat = (data["lat"] as? Double) ?? 0.0
        self.lon = (data["lon"] as? Double) ?? 0.0
        self.time = (data["time"] as? Timestamp)?.dateValue()
        self.symptoms = [:]
        self.predictions = []
    }
    
    static let testHistory = [
        DiagnosisHistory(["lat" : 10.0, "lon" : 20.0, "time" : Timestamp(date: Date() - 24 * 3600)]),
        DiagnosisHistory(["lat" : 10.0, "lon" : 20.0, "time" : Timestamp(date: Date() - 72 * 3600)]),
    ]
}
