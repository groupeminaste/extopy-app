//
//  AccountView.swift
//  Extopy
//
//  Created by Nathan Fallet on 10/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct AccountView: View {
    
    let user: CommonsUser
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(user.displayName)
            Text(user.displayName)
                .foregroundColor(.secondary)
        }
    }
    
}
