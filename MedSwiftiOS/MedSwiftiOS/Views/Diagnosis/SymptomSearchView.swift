//
//  SymptomSearchView.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 09/03/23.
//

import SwiftUI

struct SymptomSearchView: View {
    
    @EnvironmentObject var symptomVM: SymptomAnalyzerViewModel
    @Binding var path: [String]
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
            Section("Selected Symptoms") {
                ForEach(Array(symptomVM.selectedSymptoms.keys).sorted(by: { $0.name < $1.name}), id: \.self.name) { symptom in
                    NavigationLink {
                        SymptomDetailView(symptom: symptom)
                    } label: {
                        Text(symptom.text)
                    }
                }
                .onDelete { indexSet in
                    let deletedSymptom = Array(symptomVM.selectedSymptoms.keys).sorted(by: { $0.name < $1.name})[indexSet.first!]
                    symptomVM.selectedSymptoms.removeValue(forKey: deletedSymptom)
                }
            }
            
            if !symptomVM.suggestedSymptoms.isEmpty && self.searchText.isEmpty {
                Section("Suggested Symptoms") {
                    ForEach(symptomVM.suggestedSymptoms, id: \.self) { symptom in
                        if !Array(symptomVM.selectedSymptoms.keys).contains(where: { s in
                           s == symptom
                        }) {
                            NavigationLink {
                                SymptomDetailView(symptom: symptom)
                            } label: {
                                Text(symptom.text)
                            }
                        }
                    }
                }
            }
            Section("Add New Symptoms") {
                ForEach(filteredSymptoms, id: \.self.name) { symptom in
                    NavigationLink {
                        SymptomDetailView(symptom: symptom)
                    } label: {
                        ZStack(alignment: .topLeading) {
                            Text(symptom.text)
                            if symptomVM.selectedSymptoms[symptom] != nil {
                                Image(systemName: "checkmark.circle.fill")
                                    .font(.system(size: 12))
                                    .foregroundColor(.green)
                                    .offset(CGSize(width: -12, height: -5))
                            }
                        }
                    }
                }
            }
        }
        .toolbar {
            ToolbarItem(placement: .confirmationAction) {
                Button("Diagnose") {
                    if path.count > 1 {
                        let _ = path.popLast()
                    } else {
                        path.append("diagnosis")
                    }
                    Task {
                        await symptomVM.getAnalysis()
                    }
                }
            }
        }
        .searchable(text: $searchText)
        .navigationTitle("Add Symptoms")
        
    }
}

struct SymptomSearchView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationView {
            SymptomSearchView(path: .constant(["symptom"]))
                .environmentObject(SymptomAnalyzerViewModel(UserEntity.testUser))
        }
    }
}
