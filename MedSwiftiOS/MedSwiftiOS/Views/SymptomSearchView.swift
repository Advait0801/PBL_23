//
//  SymptomSearchView.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 09/03/23.
//

import SwiftUI

struct SymptomSearchView: View {
    
    @EnvironmentObject var symptomVM: SymptomAnalyzerViewModel
    
    @State var searchText = ""
    
    var filteredSymptoms: [Symptom] {
        if searchText.isEmpty {
            return symptomVM.api.symptoms
        } else {
            return symptomVM.api.symptoms.filter { $0.name.localizedCaseInsensitiveContains(searchText) }
        }
    }
    var body: some View {
        List {
            ForEach(filteredSymptoms, id: \.self.name) { symptom in
                NavigationLink {
                    SymptomDetailView(symptom: symptom, value: .constant("20"))
                } label: {
                    Text(symptom.text)
                }
            }
        }.searchable(text: $searchText)
    }
}

struct SymptomSearchView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationView {
            SymptomSearchView()
                .environmentObject(SymptomAnalyzerViewModel())
        }
    }
}
