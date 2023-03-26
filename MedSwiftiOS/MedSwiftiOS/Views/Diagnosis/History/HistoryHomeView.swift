//
//  HistoryHomeView.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 25/03/23.
//

import SwiftUI

struct HistoryHomeView: View {
    @EnvironmentObject var symptomVM: SymptomAnalyzerViewModel
    var body: some View {
        NavigationStack {
            List {
                if symptomVM.history.isEmpty {
                    Text("Pull To Fetch")
                } else {
                    ForEach(symptomVM.history, id: \.self) { historyData in
                        NavigationLink {
                            HistoryDetailView(historyData: historyData)
                        } label: {
                            HStack {
                                Image(systemName: "calendar")
                                if let dt = historyData.time {
                                    Text(dt, style: .date)
                                } else {
                                    Text("Unknown Time")
                                }
                            }
                        }
                    }
                }
            }
            .navigationTitle("History")
            .refreshable {
                Task { await symptomVM.getHistory() }
            }
        }
    }
}

struct HistoryHomeView_Previews: PreviewProvider {
    static var previews: some View {
        HistoryHomeView()
            .environmentObject(SymptomAnalyzerViewModel(UserEntity.testUser))
    }
}
