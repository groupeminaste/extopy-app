//
//  SettingsViewModel.swift
//  Extopy
//
//  Created by Nathan Fallet on 09/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

class SettingsViewModel: ObservableObject {
    
    @Published var accounts = [Extopy_commonsUser]()
    @Published var badge = 0
    
    func handleAccountClick(account: Extopy_commonsUser) {
        
    }
    
}
