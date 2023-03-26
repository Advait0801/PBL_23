//
//  Syringe.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 22/03/23.
//

import SwiftUI

import SwiftUI

struct SyringeView: View {
    var fillAmount: CGFloat
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 10)
                .frame(width: 40, height: min(120 * fillAmount, 120))
                .foregroundColor(.blue)
                .offset(.init(width: 0, height: 60 * min(1, 1 - fillAmount)))
            Image(systemName: "syringe")
                .font(.system(size: 144, weight: .ultraLight))
                .rotationEffect(.degrees(-45))
        }
        .animation(.easeInOut, value: fillAmount)
    }
}

struct SyringeView_Previews: PreviewProvider {
    static var previews: some View {
        SyringeView(fillAmount: 1)
    }
}
