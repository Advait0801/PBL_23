//
//  DiagnosisBeginView.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 13/03/23.
//

import SwiftUI

struct DiagnosisBeginView: View {
    @EnvironmentObject var symptomVM: SymptomAnalyzerViewModel
    var body: some View {
        NavigationView {
            VStack {
                Image("diagnosis.welcome")
                    .resizable()
                    .scaledToFit()
                NavigationLink {
                    SymptomSearchView()
                        .navigationTitle("Symptoms")
                } label: {
                    Text("Begin Diagnosis")
                }

            }
            .navigationTitle("Diagnosis")
        }
    }
}

struct DiagnosisBeginView_Previews: PreviewProvider {
    static var previews: some View {
        DiagnosisBeginView()
            .environmentObject(SymptomAnalyzerViewModel(UserEntity.testUser))
    }
}
