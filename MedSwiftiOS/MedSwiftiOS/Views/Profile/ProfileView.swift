//
//  ProfileView.swift 
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 03/03/23.
//

import SwiftUI
import PhotosUI

struct ProfileView: View {
    @EnvironmentObject var loginVM: LoginViewModel
    
    @State var isEditing = false
    @State var photosPickerItem: PhotosPickerItem? = nil
    
    @State var profileImage: UIImage?
    @State var profileImageProgressVisibile: Bool = false
    @State var profileImageProgressValue: Double = 0.0
    
    var userEntity: UserEntity {
        if let user = loginVM.currentUserEntity {
            return user
        } else {
            DispatchQueue.main.async {
                loginVM.state = .loggedOut
            }
            return UserEntity.testUser
        }
    }
    
    
    var body: some View {
        NavigationView {
            List {
                HStack {
                    Spacer()
                    VStack {
                        ZStack {
                            if let img = profileImage {
                                Image(uiImage: img)
                                    .resizable()
                                    .scaledToFill()
                                    .frame(width: 144, height: 144)
                                    .overlay(alignment: .bottom) {
                                        PhotosPicker(selection: $photosPickerItem) {
                                            HStack {
                                                Spacer()
                                                Text("CHANGE")
                                                    .font(.caption)
                                                    .foregroundStyle(.secondary)
                                                    .foregroundColor(.primary)
                                                    .padding(.vertical, 6)
                                                Spacer()
                                            }
                                            .background(.ultraThinMaterial)
                                        }
                                    }
                            } else {
                                Image(systemName: "person.circle")
                                    .resizable()
                                    .scaledToFill()
                                    .frame(width: 144, height: 144)
                                    .overlay(alignment: .bottom) {
                                        PhotosPicker(selection: $photosPickerItem) {
                                            HStack {
                                                Spacer()
                                                Text("CHANGE")
                                                    .font(.caption)
                                                    .foregroundStyle(.secondary)
                                                    .foregroundColor(.primary)
                                                    .padding(.vertical, 6)
                                                Spacer()
                                            }
                                            .background(.ultraThinMaterial)
                                        }
                                    }
                            }
                        }
                        .overlay {
                            if profileImageProgressVisibile {
                                CircularProgressView(value: profileImageProgressValue)
                            }
                        }
                        .clipShape(Circle())
                        .padding(.bottom, 4)
                        
                        Text(userEntity.firstName + " " + userEntity.lastName)
                            .font(.title)
                            .bold()
                    }
                    .onChange(of: photosPickerItem) { newValue in
                        Task {
                            do {
                                guard let data = try await newValue?.loadTransferable(type: Data.self) else { return }
                                if let uiImage = UIImage(data: data) {
                                    DispatchQueue.main.async {
                                        profileImage = uiImage
                                    }
                                }
                            } catch {
                                print(error.localizedDescription)
                            }
                        }
                    }
                    .onChange(of: profileImage) { _ in
                        Task {
                            await loginVM.uploadImage(
                                self.$profileImage,
                                profileImageProgressVisibile: self.$profileImageProgressVisibile,
                                profileImageProgressValue: self.$profileImageProgressValue
                            )
                        }
                    }
                    Spacer()
                }
                .padding()
                Section {
                    HStack {
                        Image(systemName: "envelope.fill")
                            .frame(width: 32)
                        Text(userEntity.email)
                            .tint(.primary)
                    }
                    HStack {
                        Image(systemName: "phone.fill")
                            .frame(width: 32)
                        Text(userEntity.phoneNumber)
                            .tint(.primary)
                    }
                    HStack {
                        Image(systemName: "calendar")
                            .frame(width: 32)
                        Text(userEntity.dateOfBirth, style: .date)
                    }
                    HStack {
                        Image(systemName: "drop.fill")
                            .frame(width: 32)
                        Text(userEntity.bldGrp.rawValue)
                    }
                    HStack {
                        Image(systemName: "scalemass.fill")
                            .frame(width: 32)
                        Text("\(userEntity.weight, specifier: "%.1f") kg")
                    }
                    HStack {
                        Image(systemName: "ruler")
                            .frame(width: 32)
                        Text("\(userEntity.height, specifier: "%.1f") cm")
                    }
                }
                
                Section {
                    Button {
                        Task {
                            await loginVM.resetPassword()
                        }
                    } label: {
                        HStack {
                            Spacer()
                            Label("Reset Password", systemImage: "arrow.2.squarepath")
                            Spacer()
                        }
                    }
                    Button {
                        loginVM.logoutAcion()
                    } label: {
                        HStack {
                            Spacer()
                            Label("Sign Out", systemImage: "rectangle.portrait.and.arrow.right")
                            Spacer()
                        }
                        .foregroundColor(.white)
                    }
                    .listRowBackground(Color.red)
                }
            }
            .navigationTitle("Profile")
            .toolbar {
                ToolbarItem {
                    Button {
                        self.isEditing.toggle()
                    } label: {
                        Label("Edit", systemImage: "pencil")
                            .labelStyle(.titleAndIcon)
                    }
                }
            }
        }
        .sheet(isPresented: $isEditing) {
            EditProfileView(userData: userEntity, onConfirm: loginVM.updateProfile)
        }
        .onAppear { Task {
            guard let uid = loginVM.currentUserEntity?.id else { return }
            await loginVM.fetchImage(
                $profileImage,
                profileImageProgressVisibile: $profileImageProgressVisibile,
                profileImageProgressValue: $profileImageProgressValue,
                uid: uid
            )
        } }
    }
}

struct ProfileView_Previews: PreviewProvider {
    static var previews: some View {
        ProfileView()
            .environmentObject(LoginViewModel())
    }
}
