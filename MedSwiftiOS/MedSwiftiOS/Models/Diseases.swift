//
//  Diseases.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 14/03/23.
//

import Foundation

// MARK: - Welcome6Element
struct Disease: Codable {
    let name, text, laytext: String
    let category: Category
    let alias: String?
    let wiki, wiki2, wiki3, wiki4: String?
    let isRare, isGenderSpecific, isImmLifeThreatening, isCantMiss: Bool?
    let risk: Int?
    let icd10, loinc: String?
    let gencount: Int?
}

enum Category: Codable {
    case acute
    case chronic
}
