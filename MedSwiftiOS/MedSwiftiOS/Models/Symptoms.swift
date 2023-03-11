//
//  Symptoms.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 08/03/23.
//

import Foundation

// MARK: - Symptom
struct Symptom: Codable, Hashable {
    let text: String
    let laytext: String
    let name: String
    let type: TypeEnum
    let min, max: Double?
    let welcomeDefault: Double
    let category: String
    let alias: String
    let wiki: String
    let subcategory1: String
    let subcategory2: String
    let subcategory3: String
    let subcategory4: String
    let isPatientProvided: Bool
    let step: Double?
    let choices: [Choice]?

    enum CodingKeys: String, CodingKey {
        case text, laytext, name, type, min, max
        case welcomeDefault = "default"
        case category, alias, wiki, subcategory1, subcategory2, subcategory3, subcategory4
        case isPatientProvided = "IsPatientProvided"
        case step, choices
    }
}

// MARK: - Choice
struct Choice: Codable, Hashable {
    let text, laytext: String
    let value: Int
    let relatedanswertag: String?
}

enum TypeEnum: String, Codable, Hashable {
    case categorical = "categorical"
    case double = "double"
    case integer = "integer"
}

typealias Symptoms = [Symptom]


