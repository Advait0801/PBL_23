//
//  DiseaseCardView.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 15/03/23.
//

import SwiftUI

struct DiseaseCardView: View {
    let data: DiseaseProbability
    var body: some View {
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
        .padding()
        .background {
            RoundedRectangle(cornerRadius: 16)
                .foregroundColor(.orange)
        }
    }
}

struct DiseaseCardView_Previews: PreviewProvider {
    static var previews: some View {
        DiseaseCardView(data: DiseaseProbability(disease: SymptomAnalyser().diseases["Community acquired pneumonia (CAP)"]!, probability: 0.02))
            .padding()
    }
}
