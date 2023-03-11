//
//  VerifyEmailView.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 26/02/23.
//

import SwiftUI

struct VerifyEmailView: View {
    
    @Binding var isVisible: Bool
    
    var body: some View {
        VStack {
            Image("email.confirmed")
                .resizable()
                .scaledToFit()
                .padding()
            Text("Please click the verification link sent to your email address.").multilineTextAlignment(.center)
        }
        .padding()
        .toolbar {
            ToolbarItem(placement: .confirmationAction) {
                Button("Done") {
                    isVisible = false
                }
            }
        }
    }
}

struct VerifyEmailVIew_Previews: PreviewProvider {
    static var previews: some View {
        VerifyEmailView(isVisible: .constant(true))
    }
}
