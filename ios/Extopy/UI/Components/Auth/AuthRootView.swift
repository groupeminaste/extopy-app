//
//  AuthRootView.swift
//  Extopy
//
//  Created by Nathan Fallet on 22/06/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct AuthRootView: View {
    
    let loginClicked: () -> Void
    
    var body: some View {
        VStack(spacing: 16) {
            Spacer()
            
            LottieView(lottieFile: "social")
                .frame(maxHeight: 300)
            
            Spacer()
            
            Text("auth_title")
                .font(.largeTitle)
            Text("auth_description")
            
            Spacer()
            
            Button("auth_button_login", action: loginClicked)
                .buttonStyle(DefaultButtonStyle(large: true))
        }
        .multilineTextAlignment(.center)
        .padding()
        .modifier(BackgroundColorStyle())
    }
    
}

#Preview {
    AuthRootView(
        loginClicked: {}
    )
}
