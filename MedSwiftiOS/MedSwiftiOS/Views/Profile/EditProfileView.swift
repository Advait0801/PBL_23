//
//  EditProfileView.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 23/03/23.
//

import SwiftUI

struct EditProfileView: View {
    
    @State var userData: UserEntity
    @Environment(\.dismiss) var dismiss
    var onConfirm: (_ userEntity: UserEntity) async -> ()
    
    var infoForm: some View {
        Form {
            Section("Enter your information") {
                TextField("First Name", text: $userData.firstName)
                    .textContentType(.givenName)
                    .autocorrectionDisabled(true)
                TextField("Last Name", text: $userData.lastName)
                    .textContentType(.givenName)
                    .autocorrectionDisabled(true)
            }
            Section {
                TextField("Email", text: $userData.email)
                    .autocorrectionDisabled(true)
                    .textInputAutocapitalization(.never)
                    .textContentType(.emailAddress)
                TextField("Phone Number", text: $userData.phoneNumber)
                    .textContentType(.telephoneNumber)
            }
            Section {
                Picker("Blood Group", selection: $userData.bldGrp) {
                    ForEach(BloodGroup.allCases, id: \.self) { grp in
                        Text(grp.rawValue)
                    }
                }
                Picker("Gender", selection: $userData.gender) {
                    ForEach(Gender.allCases, id: \.self) { gender in
                        Text(gender.rawValue)
                    }
                }
                DatePicker(
                    "DOB",
                    selection: $userData.dateOfBirth,
                    in: ...Date(),
                    displayedComponents: .date
                )
                VStack {
                    HStack {
                        Text("Height")
                        Spacer()
                        Text("\(userData.height, specifier: "%.1f") cm")
                    }
                    Slider(value: $userData.height, in: 30 ... 240, step: 0.5)
                }
                VStack {
                    HStack {
                        Text("Weight")
                        Spacer()
                        Text("\(userData.weight, specifier: "%.1f") kg")
                    }
                    Slider(value: $userData.weight, in: 5 ... 150, step: 0.5)
                }
            }
        }
    }
    
    var body: some View {
        NavigationStack {
            infoForm
                .navigationTitle("Edit Profile")
                .toolbar {
                    ToolbarItem {
                        Button("Done") {
                            print(userData)
                            Task {
                                await onConfirm(userData)
                            }
                            dismiss()
                        }
                    }
                }
        }
    }
}

struct EditProfileView_Previews: PreviewProvider {
    @State static var userEntity = UserEntity.testUser
    static var previews: some View {
        EditProfileView(userData: userEntity, onConfirm: { _ in })
    }
}
