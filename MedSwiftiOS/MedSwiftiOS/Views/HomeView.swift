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
        if let user = loginVM.currentUserEntity {
            TabView {
                DiagnosisBeginView()
                    .tabItem {
                        Label("Diagnose", systemImage: "list.clipboard.fill")
                    }
                HistoryHomeView()
                    .tabItem {
                        Label("History", systemImage: "clock.arrow.circlepath")
                    }
                ProfileView()
                    .tabItem {
                        Label("Profile", systemImage: "person.fill")
                    }
            }
            .environmentObject(SymptomAnalyzerViewModel(user))
//            .transition(.opacity)
        } else {
            Text("Invalid State")
        }
    }
}
