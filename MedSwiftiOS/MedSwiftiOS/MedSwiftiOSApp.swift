//
//  MedSwiftiOSApp.swift
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 23/02/23.
//

import SwiftUI
import Firebase

@main
struct MedSwiftiOSApp: App {
    
    init() {
        FirebaseApp.configure()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .environmentObject(LoginViewModel())
        }
    }
}
