//
//  LoginPageView.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 23/02/23.
//

import SwiftUI

struct LoginView: View {
    @State var password = "password"
    @State var email = "crpatil1901@gmail.com"
    @State var isShowingSheet = false
    @State var isShowingVerificationScreen = false
    @EnvironmentObject var loginVM: LoginViewModel
    var body: some View {
        NavigationStack {
            ZStack {
                Color.red.ignoresSafeArea()
                Circle()
                    .foregroundColor(.primary.opacity(0.3))
                    .scaleEffect(loginVM.animating ? 4.0 : 1.7)
                    .colorInvert()
                Circle()
                    .foregroundColor(.primary)
                    .scaleEffect(loginVM.animating ? 4.0 : 1.4)
                    .colorInvert()
                VStack {
                    HStack {
                        Text("MedSwift")
                            .foregroundColor(.red)
                            .bold()
                            .font(.largeTitle)
                        Image(systemName: "cross.fill")
                            .resizable()
                            .foregroundColor(.red)
                            .frame(width: 32, height: 32)
                            .offset(CGSize(width: -5, height: -20))
                    }
                    ZStack(alignment: .trailing) {
                        VStack {
                            TextField("Email", text: $email)
                                .textContentType(.emailAddress)
                                .autocorrectionDisabled(true)
                                .textInputAutocapitalization(.never)
                                .padding()
                                .background {
                                    RoundedRectangle(cornerRadius: 8)
                                        .stroke(lineWidth: 2)
                                        .foregroundColor(.red)
                                }
                                .padding(.vertical, 8)
                            .padding(.horizontal, 48)
                            SecureField("Password", text: $password)
                                .textContentType(.password)
                                .padding()
                                .background {
                                    RoundedRectangle(cornerRadius: 8)
                                        .stroke(lineWidth: 2)
                                        .foregroundColor(.red)
                                }
                                .padding(.horizontal, 48)
                        }
                        Button {
                            Task {
                                let result = await loginVM.loginAction(email, pass: password)
                                if result.user != nil && !result.verified {
                                    DispatchQueue.main.async {
                                        isShowingVerificationScreen = true
                                    }
                                } else {
                                    print(result)
                                }
                            }
                        } label: {
                            ZStack {
                                Circle()
                                    .stroke(lineWidth: 3)
                                    .colorInvert()
                                    .frame(width: 50, height: 50)
                                Circle()
                                    .foregroundColor(.red)
                                    .frame(width: 48, height: 48)
                                Image(systemName: "arrow.right")
                                    .bold()
                                    .foregroundColor(.white)
                            }
                        }
                        .buttonStyle(LoginButtonStyle())
                        .padding(24)
                        .padding(.top, 8)
                    }
                    HStack {
                        Text("Are you new here?")
                        Button("Sign Up") { isShowingSheet = true }
                    }
                    .padding()
                } // UI
                .opacity(loginVM.animating ? 0.0 : 1.0)
                if loginVM.isLoading {
                    ZStack {
                        Color.black.opacity(0.3).ignoresSafeArea()
                        ProgressView()
                            .padding()
                            .tint(.white)
                            .background { RoundedRectangle(cornerRadius: 16).foregroundColor(.black) }
                    }
                }
            }
        }
        .navigationViewStyle(.stack)
        .sheet(isPresented: $isShowingSheet) {
            RegistrationView(isVisible: $isShowingSheet)
        }
        .sheet(isPresented: $isShowingVerificationScreen, content: {
            NavigationView {
                VerifyEmailView(isVisible: $isShowingVerificationScreen)
            }
        })
        .alert(loginVM.errorDesc, isPresented: $loginVM.isShowingError, actions: {
            Button("Okay") {
                loginVM.isShowingError = false
            }
        })
        .tint(.red)
    }
}

struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView()
            .environmentObject(LoginViewModel())
    }
}

struct LoginButtonStyle: ButtonStyle {
    func makeBody(configuration: Configuration) -> some View {
        configuration.label
            .overlay {
                Circle()
                    .opacity(configuration.isPressed ? 0.3 : 0.0)
                    .colorInvert()
            }
    }
}
