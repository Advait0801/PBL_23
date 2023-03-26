//
//  ContentView.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 23/02/23.
//

import SwiftUI

struct ContentView: View {
    @EnvironmentObject var loginVM: LoginViewModel
    var body: some View {
        switch loginVM.state {
            case .loggedOut: LoginView()
            case .loggedIn: HomeView()
        }   
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
            .environmentObject(LoginViewModel())
    }
}
