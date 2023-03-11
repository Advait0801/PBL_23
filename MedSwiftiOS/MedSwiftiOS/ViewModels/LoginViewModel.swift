//
//  LoginViewModel.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 24/02/23.
//

import Foundation

enum LoginScreenState {
    case loggedOut
    case loggedIn
}

class LoginViewModel: ObservableObject {
    
    @Published var state: LoginScreenState = .loggedOut
    @Published var isLoading = false
    @Published var isShowingError = false
    var currentUserEntity: UserEntity?
    var errorDesc = ""
    
    
    let manager = FirebaseManager.shared
    
    func registrationAction(_ user: UserEntity, pass: String) async {
        DispatchQueue.main.async {
            self.isLoading = true
        }
        do {
            try await manager.auth.createUser(withEmail: user.email, password: pass)
            try await manager.auth.currentUser?.sendEmailVerification()
            
            guard let uid = manager.auth.currentUser?.uid else {
                errorDesc = "Could not create user."
                return
            }
            
            var userWithID = user
            userWithID.id = uid
            
            guard let _ = try? manager.db.collection("users").document(uid).setData(from: userWithID) else {
                print("User not created")
                return
            }
            
        } catch {
            self.errorDesc = error.localizedDescription
            DispatchQueue.main.async {
                self.isLoading = false
                self.isShowingError = true
            }
            print(error.localizedDescription)
        }
        DispatchQueue.main.async {
            self.isLoading = false
        }
    }
    
    func loginAction(_ email: String, pass: String) async -> (user: UserEntity?, verified: Bool) {
        do {
            DispatchQueue.main.async {
                self.isLoading = true
            }
            try await manager.auth.signIn(withEmail: email, password: pass)
            guard let uid = manager.auth.currentUser?.uid else {
                print("UID not found")
                DispatchQueue.main.async {
                    self.isLoading = false
                }
                return (nil, false)
            }
            let userEntity = try await manager.db.collection("users").document(uid).getDocument(as: UserEntity.self)
            guard let isVerified = manager.auth.currentUser?.isEmailVerified else {
                print("User not verified")
                DispatchQueue.main.async {
                    self.isLoading = false
                }
                return (nil, false)
            }
            if !isVerified {
                print("Not Verified")
                try? await manager.auth.currentUser?.sendEmailVerification()
                self.currentUserEntity = userEntity
                DispatchQueue.main.async {
                    self.state = .loggedOut
                    self.isLoading = false
                }
                return (userEntity, false)
            } else {
                print("Verified")
            }
            DispatchQueue.main.async {
                self.currentUserEntity = userEntity
                self.state = .loggedIn
                self.isLoading = false
            }
            return (userEntity, isVerified)
        } catch {
            self.errorDesc = error.localizedDescription
            DispatchQueue.main.async {
                self.isLoading = false
                self.isShowingError = true
            }
            print(error.localizedDescription)
        }
        DispatchQueue.main.async {
            self.isLoading = false
        }

        return (nil, false)
    }   
}
