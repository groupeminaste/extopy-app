//
//  UserHeaderView.swift
//  Extopy
//
//  Created by Nathan Fallet on 22/12/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import Kingfisher
import shared

struct UserHeaderView: View {
    
    let user: Extopy_commonsUser
    
    var body: some View {
        HStack(alignment: .top) {
            if let avatar = user.avatar, let url = URL(string: avatar) {
                KFImage(url)
                    .resizable()
                    .frame(width: 44, height: 44)
                    .cornerRadius(22)
            }
            VStack(alignment: .leading, spacing: 2) {
                HStack {
                    Text(user.displayName)
                    if user.verified == true {
                        Image(systemName: "checkmark.seal.fill")
                    }
                }
                Text(user.username)
                    .foregroundColor(.secondary)
            }
        }
    }
    
}
