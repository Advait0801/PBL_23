//
//  SymptomDetailView.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 09/03/23.
//

import SwiftUI

struct SymptomDetailView: View {
    var symptom: Symptom
    @EnvironmentObject var symptomVM: SymptomAnalyzerViewModel
    @Environment(\.dismiss) var dismiss
    @State var value: String = ""
    var body: some View {
        List {
            Text(symptom.text)
            Text(symptom.laytext)
                .font(.callout)
            if symptom.type == .integer {
                HStack {
                    Text("Enter Value")
                    Spacer()
                    Button {
                        guard let num = Double(value) else { return }
                        value = String(num - 1)
                    } label: {
                        Image(systemName: "minus")
                    }
                    .buttonStyle(.borderless)
                    TextField("Value", text: $value)
                        .multilineTextAlignment(.center)
                        .frame(width: 48)
                        .keyboardType(.decimalPad)
                    Button {
                        guard let num = Double(value) else { return }
                        value = String(num + 1)
                    } label: {
                        Image(systemName: "plus")
                    }
                    .buttonStyle(.borderless)
                }
            } else if symptom.type == .double {
                HStack {
                    Text("Enter Value")
                    Spacer()
                    TextField("Value", text: $value)
                        .multilineTextAlignment(.trailing)
                        .keyboardType(.decimalPad)
                }
            } else {
                Section("Choose an Option") {
                    Picker("Choose an Option", selection: $value) {
                        ForEach(symptom.choices!, id: \.self) { choice in
                            Text(choice.laytext)
                                .tag("\(choice.value)")
                        }
                    }
                    .pickerStyle(.inline)
                    .labelsHidden()
                }
                
            }
        }
        .toolbar {
            ToolbarItem(placement: .confirmationAction) {
                Button("Done") {
                    guard let value = Double(self.value) else { return }
                    if value != symptom.defaultVal {
                        symptomVM.setSymptom(symptom, value: value)
                    } else {
                        symptomVM.deleteSymptom(symptom)
                    }
                    dismiss()
                }
            }
        }
        .onAppear {
            if let setVal = symptomVM.selectedSymptoms[symptom] {
                self.value = String(format: "%.2f", setVal)
            } else {
                self.value = "\(symptom.defaultVal)"
            }
        }
        .navigationTitle("Answer the Question")
        .navigationBarTitleDisplayMode(.inline)
    }
}

struct SymptomDetailView_Previews: PreviewProvider {
    static var previews: some View {
        SymptomDetailView(symptom: SymptomAnalyser().symptoms[9])
            .environmentObject(SymptomAnalyzerViewModel(UserEntity.testUser))
    }
}
