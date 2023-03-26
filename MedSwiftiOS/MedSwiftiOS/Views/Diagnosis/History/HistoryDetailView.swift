//
//  HistoryDetailView.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 25/03/23.
//

import SwiftUI

struct HistoryDetailView: View {
    let historyData: DiagnosisHistory
    var body: some View {
        List {
            Section {
                if let dt = historyData.time {
                    Text(dt, style: .date)
                    Text(dt, style: .time)
                } else {
                    Text("Unknown Time")
                }
            }
            Section(header: Text("Predictions")) {
                ForEach(historyData.predictions, id: \.self) { pred in
                    HStack {
                        Text(pred.disease.name)
                        Spacer()
                        Text("\(pred.probability, specifier: "%.2f")%")
                    }
                }
            }
            Section(header: Text("Symptoms")) {
                ForEach(historyData.symptoms.map({ pair in
                    (pair.key, pair.value)
                }), id: \.0) { pair in
                    VStack(alignment: .leading) {
                        Text(pair.0.name)
                        if pair.0.type == .categorical {
                            Text("\(pair.0.choices!.first(where: { $0.value == Int(pair.1) })!.text)")
                                .foregroundStyle(.secondary)
                        } else {
                            Text("\(pair.1, specifier: "%.2f")")
                                .foregroundStyle(.secondary)
                        }
                    }
                }
            }
        }
    }
}

struct HistoryDetailView_Previews: PreviewProvider {
    static var previews: some View {
        HistoryDetailView(historyData: DiagnosisHistory.testHistory.first!)
    }
}
