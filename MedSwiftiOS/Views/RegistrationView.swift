//
//  RegistrationView.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 23/02/23.
//

import SwiftUI

struct RegistrationView: View {
    @Environment(\.colorScheme) private var colorScheme
    @State var height = 160.0
    @State var weight = 60.0
    
    var body: some View {
        NavigationView {
            Form {
                Section("Enter your information") {
                    TextField("First Name", text: .constant(""))
                    TextField("Last Name", text: .constant(""))
                }
                Section {
                    TextField("Email", text: .constant(""))
                    SecureField("Password", text: .constant(""))
                }
                Section {
                    Picker("Blood Group", selection: .constant("Select your blood type")) {
                        Text("A+")
                        Text("A-")
                        Text("AB+")
                        Text("AB-")
                        Text("B+")
                        Text("B-")
                        Text("O+")
                        Text("O-")
                    }
                    DatePicker("DOB", selection: .constant(.now), in: ...Date())
                    VStack {
                        HStack {
                            Text("Height")
                            Spacer()
                            Text("\(height, specifier: "%.1f") cm")
                        }
                        Slider(value: $height, in: 30 ... 240, step: 0.5)
                    }
                    VStack {
                        HStack {
                            Text("Weight")
                            Spacer()
                            Text("\(weight, specifier: "%.1f") kg")
                        }
                        Slider(value: $weight, in: 5 ... 150, step: 0.5)
                    }
                }
            }
            .toolbar {
                ToolbarItem(placement: .confirmationAction) {
                    Button("Done") { }
                }
                ToolbarItem(placement: .cancellationAction) {
                    Button("Cancel") { }
                }
            }
            .navigationTitle("Register")
        }
        .tint(.red)
    }
}

struct RegistrationView_Previews: PreviewProvider {
    static var previews: some View {
        RegistrationView()
    }
}
