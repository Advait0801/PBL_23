//
//  HomeView.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 13/03/23.
//

import SwiftUI

struct HomeView: View {
    @EnvironmentObject var loginVM: LoginViewModel
    var body: some View {
        TabView {
            ProfileView()
                .tabItem {
                    Label("Profile", systemImage: "person.fill")
                }
            DiagnosisBeginView()
                .environmentObject(SymptomAnalyzerViewModel(loginVM.currentUserEntity ?? UserEntity.testUser))
                .tabItem {
                    Label("Diagnose", systemImage: "list.clipboard.fill")
                }
        }
    }
}
