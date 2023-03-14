//
//  RegistrationView.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 23/02/23.
//

import SwiftUI

struct RegistrationView: View {
    @Environment(\.colorScheme) private var colorScheme
    @EnvironmentObject var loginVM: LoginViewModel
    @State var userData = UserEntity(firstName: "", lastName: "", phoneNumber: "", email: "", bldGrp: .Ap, dateOfBirth: Date(), height: 150.0, weight: 60.0)
    @State private var password = ""
    @State var isShowingVerificationScreen = false
    @Binding var isVisible: Bool
    
    var infoForm: some View {
        Form {
            Section("Enter your information") {
                TextField("First Name", text: $userData.firstName)
                    .textContentType(.givenName)
                TextField("Last Name", text: $userData.lastName)
                    .textContentType(.givenName)
            }
            Section {
                TextField("Email", text: $userData.email)
                    .autocorrectionDisabled(true)
                    .textInputAutocapitalization(.never)
                    .textContentType(.emailAddress)
                TextField("Phone Number", text: $userData.phoneNumber)
                    .textContentType(.telephoneNumber)
                SecureField("Password", text: $password)
                    .textContentType(.password)
            }
            Section {
                Picker("Blood Group", selection: $userData.bldGrp) {
                    ForEach(BloodGroup.allCases, id: \.self) { grp in
                        Text(grp.rawValue)
                    }
                }
                DatePicker(
                    "DOB",
                    selection: .constant(.now),
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
        ZStack {
            NavigationView {
                infoForm
                    .overlay {
                        NavigationLink(destination: VerifyEmailView(isVisible: $isVisible), isActive: self.$isShowingVerificationScreen) {
                             EmptyView()
                        }
                        .hidden()
                    }
                    .toolbar {
                        ToolbarItem(placement: .confirmationAction) {
                            Button("Done") {
                                Task {
                                    await loginVM.registrationAction(userData, pass: password)
                                    DispatchQueue.main.async {
                                        if !loginVM.isShowingError {
                                            isShowingVerificationScreen = true
                                        }
                                    }
                                }
                            }
                            NavigationLink {
                                VerifyEmailView(isVisible: $isVisible)
                            } label: {
                                    NavigationLink("Done") {
                                        VerifyEmailView(isVisible: $isVisible)
                                            .onAppear {
                                                Task {
                                                    await loginVM.registrationAction(userData, pass: password)
                                                }
                                            }
                                    }
                            }
                            .disabled(!userData.isValid() || password.isEmpty)
                        }
                        ToolbarItem(placement: .cancellationAction) {
                            Button("Cancel") { isVisible = false }
                        }
                    }
                    .navigationTitle("Register")

            }
            if loginVM.isLoading {
                ZStack {
                    Color.black.opacity(0.3).ignoresSafeArea()
                    ProgressView()
                        .padding()
                        .tint(.white)
                        .background { RoundedRectangle(cornerRadius: 16) }
                }
            }
        }
        
        .tint(.red)
        .alert(loginVM.errorDesc, isPresented: $loginVM.isShowingError, actions: {
            Button("Okay") {
                loginVM.isShowingError = false
            }
        })
    }
}

struct RegistrationView_Previews: PreviewProvider {
    static var previews: some View {
        RegistrationView(isVisible: .constant(true))
    }
}
