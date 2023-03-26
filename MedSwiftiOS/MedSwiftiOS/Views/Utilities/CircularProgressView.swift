//
//  CircularProgressView.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 24/03/23.
//

import SwiftUI

struct CircularProgressView: View {
    let value: Double
    var body: some View {
        Circle()
            .trim(from: 0.0, to: value)
            .stroke(lineWidth: 8)
            .foregroundColor(.blue)
            .rotationEffect(.degrees(-90))
            .animation(.easeInOut(duration: 0.75), value: value)
    }
}

struct CircularProgressView_Previews: PreviewProvider {
    static var previews: some View {
        CircularProgressView(value: 0.95)
    }
}
