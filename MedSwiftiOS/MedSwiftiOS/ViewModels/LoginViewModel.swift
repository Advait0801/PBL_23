//
//  LoginViewModel.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 24/02/23.
//

import SwiftUI

enum LoginScreenState {
    case loggedOut
    case loggedIn
}

class LoginViewModel: ObservableObject {
    
    @Published var state: LoginScreenState = .loggedOut
    @Published var isLoading = false
    @Published var isShowingError = false
    @Published var animating = false
    
    var currentUserEntity: UserEntity?
    
    var isInitialDownloadComplete = false
    
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
            }
            DispatchQueue.main.async {
                self.currentUserEntity = userEntity
                withAnimation(.easeIn(duration: 0.7)) {
                    self.animating = true
                    self.isLoading = false
                }
            }
            DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) {
                withAnimation { self.state = .loggedIn }
            }
            DispatchQueue.main.asyncAfter(deadline: .now() + 1.0) {
                self.animating = false
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
    
    func updateProfile(userEntity: UserEntity) async {
        guard let uid = userEntity.id else {
            print("UID not found")
            return
        }
        do {
            try manager.db.collection("users").document(uid).setData(from: userEntity)
            DispatchQueue.main.async {
                self.currentUserEntity = userEntity
            }
        } catch {
            print(error.localizedDescription)
        }
    }
    
    func resetPassword() async {
        guard let email = currentUserEntity?.email else { return }
        do {
            try await manager.auth.sendPasswordReset(withEmail: email)
        } catch {
            print(error.localizedDescription)
        }
    }
    
    func uploadImage(
        _ profileImage: Binding<UIImage?>,
        profileImageProgressVisibile: Binding<Bool>,
        profileImageProgressValue: Binding<Double>
    ) async {
        if !self.isInitialDownloadComplete { return }
        guard let image = profileImage.wrappedValue else {
            print("Image not found")
            return
        }
        guard let imageJPEG = image.jpegData(compressionQuality: 0.5) else {
            print("Couldn't convert to JPEG")
            return
        }
        guard let uid = currentUserEntity?.id else {
            print("User doesn't have UID")
            return
        }
        let storageRef = manager.storage.reference().child("profilePictures").child(uid + ".jpg")
        let uploadTask = storageRef.putData(imageJPEG)
        
        DispatchQueue.main.async {
            withAnimation { profileImageProgressVisibile.wrappedValue = true }
        }
        
        uploadTask.observe(.success) { snapshot in
            DispatchQueue.main.async {
                withAnimation { profileImageProgressVisibile.wrappedValue = false }
            }
            DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) {
                profileImageProgressValue.wrappedValue = 0.0
            }
        }
        
        uploadTask.observe(.progress) { snapshot in
            guard let value = snapshot.progress?.fractionCompleted else { return }
            DispatchQueue.main.async { withAnimation { profileImageProgressValue.wrappedValue = value } }
        }
        
        uploadTask.observe(.failure) { snapshot in
            guard let error = snapshot.error else { return }
            print(error.localizedDescription)
            DispatchQueue.main.async {
                withAnimation { profileImageProgressVisibile.wrappedValue = false }
            }
            DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) {
                profileImageProgressValue.wrappedValue = 0.0
            }
        }
    }
    
    func fetchImage(
        _ profileImage: Binding<UIImage?>,
        profileImageProgressVisibile: Binding<Bool>,
        profileImageProgressValue: Binding<Double>,
        uid: String
    ) async {
        let storageRef = manager.storage.reference().child("profilePictures").child(uid + ".jpg")
        
        let downloadTask = storageRef.getData(maxSize: 5 * 1024 * 1024) { result in
            switch result {
                case .success(let data):
                    DispatchQueue.main.async {
                        profileImage.wrappedValue = UIImage(data: data)
                        self.isInitialDownloadComplete = true
                    }
                case .failure(let error):
                    print(error.localizedDescription)
            }
        }
        
        DispatchQueue.main.async { withAnimation { profileImageProgressVisibile.wrappedValue = true } }
        
        downloadTask.observe(.success) { snapshot in
            DispatchQueue.main.async {
                withAnimation { profileImageProgressVisibile.wrappedValue = false }
            }
            DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) {
                profileImageProgressValue.wrappedValue = 0.0
            }
        }
        
        downloadTask.observe(.progress) { snapshot in
            guard let value = snapshot.progress?.fractionCompleted else { return }
            DispatchQueue.main.async { withAnimation { profileImageProgressValue.wrappedValue = value } }
        }
        
        downloadTask.observe(.failure) { snapshot in
            DispatchQueue.main.async {
                withAnimation { profileImageProgressVisibile.wrappedValue = false }
            }
            DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) {
                profileImageProgressValue.wrappedValue = 0.0
            }
        }
        
    }
    
    func logoutAcion() {
        do {
            try manager.auth.signOut()
            DispatchQueue.main.async {
                self.state = .loggedOut
                self.currentUserEntity = nil
                
            }
        } catch {
            print(error.localizedDescription)
        }
        
    }
}
