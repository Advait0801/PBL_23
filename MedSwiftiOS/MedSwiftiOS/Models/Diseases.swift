//
//  Diseases.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 14/03/23.
//

import Foundation

// MARK: - Disease
struct Disease: Codable, Hashable {
    let name, text, laytext: String
    let category: Category
    let alias: String?
    let wiki: String?
    let wiki2, wiki3, wiki4: String?
    let isRare, isGenderSpecific, isImmLifeThreatening, isCantMiss: Bool?
    let risk: Int?
    let icd10, loinc: String?
    let gencount: Int?

    enum CodingKeys: String, CodingKey {
        case name, text, laytext, category, alias, wiki, wiki2, wiki3, wiki4
        case isRare = "IsRare"
        case isGenderSpecific = "IsGenderSpecific"
        case isImmLifeThreatening = "IsImmLifeThreatening"
        case isCantMiss = "IsCantMiss"
        case risk = "Risk"
        case icd10 = "ICD10"
        case loinc = "LOINC"
        case gencount
    }
}

enum Category: String, Codable {
    case acute = "acute"
    case chronic = "chronic"
}

typealias Diseases = [Disease]
