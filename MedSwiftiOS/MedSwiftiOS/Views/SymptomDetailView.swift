//
//  SymptomDetailView.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 09/03/23.
//

import SwiftUI

struct SymptomDetailView: View {
    var symptom: Symptom
    @Binding var value: String
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
                        guard let num = Int(value) else { return }
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
                        guard let num = Int(value) else { return }
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
                    .buttonStyle(.borderless)
                    TextField("Value", text: $value)
                        .multilineTextAlignment(.center)
                        .keyboardType(.decimalPad)
                    
                    .buttonStyle(.borderless)
                }
            } else {
                Section("Choose an Option") {
                    Picker("Choose an Option", selection: .constant(1)) {
                        ForEach(symptom.choices!, id: \.self) { choice in
                            Text(choice.laytext)
                                .tag(choice.value)
                        }
                    }
                    .pickerStyle(.inline)
                }
                
            }
        }
        
    }
}

struct SymptomDetailView_Previews: PreviewProvider {
    static var previews: some View {
        SymptomDetailView(symptom: SymptomAnalyser().symptoms[9], value: .constant("10"))
    }
}
