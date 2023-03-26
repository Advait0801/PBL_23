//
//  Chip.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 15/03/23.
//

import SwiftUI

struct Chip: View {
    let systemName: String
    let title: String
    var body: some View {
        Label(title, systemImage: systemName)
            .font(.footnote)
            .padding(8)
            .background {
                RoundedRectangle(cornerRadius: 64)
                    .stroke()
            }
    }
}

struct Chip_Previews: PreviewProvider {
    static var previews: some View {
        Chip(systemName: "person", title: "Hello World")
            .foregroundColor(.red)
    }
}
