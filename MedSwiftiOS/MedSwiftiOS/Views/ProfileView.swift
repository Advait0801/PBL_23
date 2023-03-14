//
//  ProfileView.swift 
//  MedSwiftiOS
//
//  Created by Chinmay Patil on 03/03/23.
//

import SwiftUI

struct ProfileView: View {
    @EnvironmentObject var loginVM: LoginViewModel
    
    var userEntity: UserEntity {
        if let user = loginVM.currentUserEntity {
            return user
        } else {
            loginVM.state = .loggedOut
            return UserEntity.testUser
        }
    }
    
    var body: some View {
        NavigationView {
            VStack(alignment: .leading) {
                HStack {
                    Image(systemName: "person.circle")
                        .font(.system(size: 72))
                    VStack(alignment: .leading) {
                        Text(userEntity.firstName + " " + userEntity.lastName)
                            .font(.title)
                            .bold()
                            .padding(.bottom, 1)
                        HStack {
                            Image(systemName: "phone.fill")
                            Text(userEntity.phoneNumber)
                        }
                    }
                    Spacer()
                }
                .padding()
                Form {
                    HStack {
                        Image(systemName: "envelope.fill")
                            .frame(width: 32)
                        Text(userEntity.email)
                            .tint(.primary)
                    }
                    HStack {
                        Image(systemName: "calendar")
                            .frame(width: 32)
                        Text(userEntity.dateOfBirth, style: .date)
                    }
                    HStack {
                        Image(systemName: "drop.fill")
                            .frame(width: 32)
                        Text(userEntity.bldGrp.rawValue)
                    }
                    HStack {
                        Image(systemName: "scalemass.fill")
                            .frame(width: 32)
                        Text("\(userEntity.weight, specifier: "%.1f") kg")
                    }
                    HStack {
                        Image(systemName: "ruler")
                            .frame(width: 32)
                        Text("\(userEntity.height, specifier: "%.1f") cm")
                    }
                }
            }
            .background {
                Color.red.ignoresSafeArea().overlay(.ultraThinMaterial)
            }
            .navigationTitle("Profile")
        }
    }
}

struct ProfileView_Previews: PreviewProvider {
    static var previews: some View {
        ProfileView()
    }
}
