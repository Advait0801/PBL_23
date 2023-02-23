//
//  LoginPageView.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 23/02/23.
//

import SwiftUI

struct LoginPageView: View {
    @State var password = ""
    @State var email = ""
    var body: some View {
        ZStack {
            Color.red.ignoresSafeArea()
            Circle()
                .foregroundColor(.primary.opacity(0.3))
                .scaleEffect(1.7)
                .colorInvert()
            Circle()
                .foregroundColor(.primary)
                .scaleEffect(1.4)
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
                            .padding()
                            .background {
                                RoundedRectangle(cornerRadius: 8)
                                    .stroke(lineWidth: 2)
                                    .foregroundColor(.red)
                            }
                            .padding(.vertical, 8)
                        .padding(.horizontal, 48)
                        SecureField("Password", text: $password)
                            .padding()
                            .background {
                                RoundedRectangle(cornerRadius: 8)
                                    .stroke(lineWidth: 2)
                                    .foregroundColor(.red)
                            }
                            .padding(.horizontal, 48)
                    }
                    Button {
                        
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
                }
                HStack {
                    Text("Are you new here?")
                    Button("Sign Up") { }
                }
                .padding()
            }
        }
        .tint(.red)
    }
}

struct LoginPageView_Previews: PreviewProvider {
    static var previews: some View {
        LoginPageView()
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
