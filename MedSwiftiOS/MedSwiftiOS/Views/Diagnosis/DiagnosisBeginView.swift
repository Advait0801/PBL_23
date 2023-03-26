//
//  DiagnosisBeginView.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 13/03/23.
//

import SwiftUI

struct DiagnosisBeginView: View {
    @EnvironmentObject var symptomVM: SymptomAnalyzerViewModel
    
    @State var path = [String]()
    
    var topArr: [String] {
        var topArr = [String]()
        let array = Array(symptomVM.selectedSymptoms.keys).map { $0.name }
        for i in 0 ..< array.count {
            if i % 2 == 0 {
                topArr.append(array[i])
            }
        }
        return topArr
    }
    
    var botArr: [String] {
        var botArr = [String]()
        let array = Array(symptomVM.selectedSymptoms.keys).map { $0.name }
        for i in 0 ..< array.count {
            if i % 2 == 1 {
                botArr.append(array[i])
            }
        }
        return botArr
    }
    
    var body: some View {
        NavigationStack(path: $path) {
            ScrollView {
                VStack(alignment: .leading) {
                    Image("diagnosis.welcome")
                        .resizable()
                        .scaledToFit()
                    Text("SYMPTOMS")
                        .font(.caption)
                        .foregroundColor(.secondary)
                        .padding(.leading)
                    ScrollView(.horizontal, showsIndicators: false) {
                        VStack(alignment: .leading) {
                            HStack {
                                ForEach(topArr, id: \.self) { i in
                                    Chip(systemName: "staroflife.fill", title: i)
                                }
                            }
                            HStack {
                                ForEach(botArr, id: \.self) { i in
                                    Chip(systemName: "staroflife.fill", title: i)
                                }
                            }
                        }
                        .foregroundColor(.blue)
                        .padding(.horizontal)
                        .padding(.vertical, 4)
                    }
                    HStack {
                        Button {
                            path.append("symptoms")
                        } label: {
                            ZStack(alignment: .leading) {
                                RoundedRectangle(cornerRadius: 16)
                                    .foregroundColor(.mint)
                                Label("Set Symptoms", systemImage: "list.clipboard")
                                    .foregroundColor(.primary)
                                    .colorInvert()
                                    .padding()
                            }
                            .aspectRatio(2.5, contentMode: .fit)
                        }
                        Button {
                            path.append("diagnosis")
                            Task {
                                await symptomVM.getAnalysis()
                            }
                        } label: {
                            ZStack(alignment: .leading) {
                                RoundedRectangle(cornerRadius: 16)
                                    .foregroundColor(.blue)
                                Label("Get Analysis", systemImage: "stethoscope")
                                    .foregroundColor(.primary)
                                    .colorInvert()
                                    .padding()
                            }
                            .aspectRatio(2.5, contentMode: .fit)
                        }
                    }
                    .padding(.horizontal)
                    HStack {
                        Button {
                            Task {
                                await symptomVM.saveDiagnosis()
                            }
                        } label: {
                            ZStack(alignment: .leading) {
                                RoundedRectangle(cornerRadius: 16)
                                    .foregroundColor(.yellow)
                                Label("Save Analysis", systemImage: "icloud.and.arrow.up")
                                    .foregroundColor(.primary)
                                    .colorInvert()
                                    .padding()
                            }
                            .aspectRatio(2.5, contentMode: .fit)
                        }
                        NavigationLink {
                            Text("Doctors")
                        } label: {
                            ZStack(alignment: .leading) {
                                RoundedRectangle(cornerRadius: 16)
                                    .foregroundColor(.pink)
                                Label("Find Doctors", systemImage: "rectangle.stack.badge.person.crop")
                                    .foregroundColor(.primary)
                                    .colorInvert()
                                    .padding()
                            }
                            .aspectRatio(2.5, contentMode: .fit)
                        }
                    }
                    .padding(.horizontal)
                }
            }
            .navigationTitle("Diagnose")
            .navigationDestination(for: String.self) { location in
                switch location {
                    case "diagnosis": DiagnosisView(path: $path)
                    case "symptoms": SymptomSearchView(path: $path)
                    case "doctor": Text("Doctors")
                    default: Text("Error in Navigation")
                }
            }
        }
        .onAppear { symptomVM.requestCLAuthorization() }
    }
}

struct DiagnosisBeginView_Previews: PreviewProvider {
    static var previews: some View {
        DiagnosisBeginView()
            .environmentObject(SymptomAnalyzerViewModel(UserEntity.testUser))
    }
}
