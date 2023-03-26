//
//  AnalysysResponseModel.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 15/03/23.
//

import Foundation

struct AnalysisResponse: Codable {
    let status: String
    let diseases: [[String : String]]

    enum CodingKeys: String, CodingKey {
        case status
        case diseases = "Diseases"
    }
}

struct DiseaseProbability: Codable, Hashable {
    let disease: Disease
    let probability: Double
}
 
struct SuggestedQuestionsResponse: Codable {
    let status: String
    let suggestedFeatures: [[String]]

    enum CodingKeys: String, CodingKey {
        case status
        case suggestedFeatures = "SuggestedFeatures"
    }
}
