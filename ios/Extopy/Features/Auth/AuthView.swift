//
//  AuthVuew.swift
//  Extopy
//
//  Created by Nathan Fallet on 22/12/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared
import KMMViewModelSwiftUI
import KMPNativeCoroutinesAsync

struct AuthView: View {
    
    @InjectStateViewModel private var viewModel: AuthViewModel
    
    @State var sheet: AuthSheet?
    
    let onUserLogged: () -> Void
    
    var body: some View {
        ZStack {
            Color.accentColor.ignoresSafeArea()
            VStack(spacing: 8) {
                Text("Time to get back to the basics.")
                
                Button("Authenticate") {
                    if let url = URL(string: viewModel.url) {
                        sheet = .safari(url: url)
                    }
                }
            }
            .foregroundColor(.white)
        }
        .sheet(item: $sheet) { sheet in
            switch (sheet) {
            case .safari(let url):
                SafariView(url: url)
            }
        }
        .onOpenURL { url in
            Task {
                guard let code = URLComponents(url: url, resolvingAgainstBaseURL: true)?.queryItems?.first(where: {
                    $0.name == "code"
                })?.value else { return }
                
                try await asyncFunction(for: viewModel.authenticate(
                    code: code,
                    onUserLogged: onUserLogged
                ))
            }
        }
    }
    
}
