//
//  Firebase.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 24/02/23.
//
  
import Firebase
import FirebaseFirestore
import FirebaseStorage

class FirebaseManager {
    static let shared = FirebaseManager()
    
    var auth: Auth
    var db: Firestore
    var storage: Storage
    
    init() {
        self.auth = Auth.auth()
        self.db = Firestore.firestore()
        self.storage = Storage.storage()
    }
}
