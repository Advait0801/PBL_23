//
//  DiagnosisView.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 15/03/23.
//

import SwiftUI

struct DiagnosisView: View {
    @EnvironmentObject var symptomVM: SymptomAnalyzerViewModel
    @Binding var path: [String]
    
    var body: some View {
        
        ZStack {
            if symptomVM.isLoading {
                VStack {
                    SyringeView(fillAmount: Double(symptomVM.progressSteps) / Double(symptomVM.totalSteps))
                        .padding()
                    Text("Loading...")
                }
                .navigationTitle("Diagnosis")
                .transition(.slide)
                .zIndex(1)
            }
            List {
                Section {
                    ForEach(symptomVM.analysis, id: \.disease) { data in
                        HStack {
                            Image(systemName: "staroflife.fill")
                                .font(.system(size: 36))
                                .padding(.trailing, 4)
                            VStack(alignment: .leading) {
                                Text(data.disease.text)
                                    .font(.title3)
                                    .lineLimit(1)
                                HStack {
                                    Text(data.disease.name)
                                        .font(.callout)
                                        .lineLimit(1)
                                    .foregroundColor(.primary.opacity(0.5))
                                    Spacer()
                                    Text("\(data.probability * 100, specifier: "%.2f")%")
                                }
                            }
                            Spacer()
                        }
                        .transition(.slide)
                    }
                } header: {
                    Text ("Possible Diseases")
                }
            }
            .refreshable {
                Task {
                    await symptomVM.getAnalysis()
                }
            }
            .transition(.slide)
        }
        .toolbar {
            ToolbarItem(placement: .confirmationAction) {
                Button("Refine Diagnosis") {
                    if path.count > 1 {
                        let _ = path.popLast()
                    } else {
                        path.append("symptoms")
                    }
                }
            }
        }
        .navigationTitle("Diagnosis")
    }
}

struct DiagnosisView_Previews: PreviewProvider {
    @ObservedObject static var vm = SymptomAnalyzerViewModel(UserEntity.testUser)
    static var previews: some View {
        DiagnosisView(path: .constant(["diagnosis"]))
            .navigationTitle("diagnosis")
            .environmentObject(vm)
            .onAppear {
                Task {
                    await vm.getAnalysis()
                }
            }
    }
}
