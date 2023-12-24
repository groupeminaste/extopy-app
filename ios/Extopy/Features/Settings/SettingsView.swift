//
//  SettingsView.swift
//  Extopy
//
//  Created by Nathan Fallet on 09/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct SettingsView: View {
    
    @Environment(\.openURL) var openURL
    
    @StateObject var viewModel: SettingsViewModel
    
    var body: some View {
        Form {
            Section(header: Text("settings_accounts")) {
                ForEach(viewModel.accounts, id: \.id) { account in
                    AccountView(user: account)
                        .onTapGesture {
                            viewModel.handleAccountClick(account: account)
                        }
                }
            }
            Section(header: Text("settings_about")) {
                Text("settings_about_developer")
                    .onTapGesture {
                        if let url = URL(string: "https://www.groupe-minaste.org/") {
                            openURL(url)
                        }
                    }
            }
        }
        .navigationTitle("settings_title")
        .badge(viewModel.badge)
    }
    
}

struct SettingsView_Previews: PreviewProvider {
    
    static var previews: some View {
        SettingsView(viewModel: SettingsViewModel())
    }
    
}
