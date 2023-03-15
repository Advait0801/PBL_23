//
//  AnalysysResponseModel.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 15/03/23.
//

import Foundation

// MARK: - AnalysisResponse
struct AnalysisResponse: Codable {
    let status: String
    let diseases: [[String : String]]

    enum CodingKeys: String, CodingKey {
        case status
        case diseases = "Diseases"
    }
}

struct DiseaseProbability {
    let disease: Disease
    let probability: Double
}
