//
//  StringExtension.swift
//  Extopy
//
//  Created by Nathan Fallet on 09/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

extension String {
    
    // Localization
    
    func localized(bundle: Bundle = .main, tableName: String = "Localizable") -> String {
        return NSLocalizedString(self, tableName: tableName, value: "**\(self)**", comment: "")
    }
    
    func format(_ args: CVarArg...) -> String {
        return String(format: self, locale: .current, arguments: args)
    }
    
    func format(_ args: [String]) -> String {
        return String(format: self, locale: .current, arguments: args)
    }
    
}
