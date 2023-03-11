//
//  UserModel.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 23/02/23.
//

import Foundation
import FirebaseFirestoreSwift

enum BloodGroup: String, CaseIterable, Codable {
    case Ap = "A+"
    case An = "A-"
    case Bp = "B+"
    case Bn = "B-"
    case ABp = "AB+"
    case ABn = "AB-"
    case Op = "O+"
    case On = "O-"
}

struct UserEntity: Codable {
    @DocumentID var id: String?
    var firstName: String
    var lastName: String
    var phoneNumber: String
    var email: String
    var bldGrp: BloodGroup
    var dateOfBirth: Date
    var height: Double
    var weight: Double
    
    init() {
        self.firstName = ""
        self.lastName = ""
        self.phoneNumber = ""
        self.email = ""
        self.bldGrp = .Ap
        self.dateOfBirth = Date()
        self.height = 150.0
        self.weight = 60.0
    }
    
    init(id: String? = nil, firstName: String, lastName: String, phoneNumber: String, email: String, bldGrp: BloodGroup, dateOfBirth: Date, height: Double, weight: Double) {
        self.id = id
        self.firstName = firstName
        self.lastName = lastName
        self.phoneNumber = phoneNumber
        self.email = email
        self.bldGrp = bldGrp
        self.dateOfBirth = dateOfBirth
        self.height = height
        self.weight = weight
    }
    
    
    func isValid() -> Bool {
        !firstName.isEmpty &&
        !lastName.isEmpty &&
        !phoneNumber.isEmpty &&
        !email.isEmpty
    }
    
    static var testUser = UserEntity(id: "testUser", firstName: "Chinmay", lastName: "Patil", phoneNumber: "+91 9860 767 300", email: "crpatil1901@gmail.com", bldGrp: .Bp, dateOfBirth: Date(), height: 188, weight: 65.5)
}
