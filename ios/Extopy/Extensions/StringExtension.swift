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
    
    // Utils
    
    func counterToSFSymbol(active: Bool) -> String {
        switch (self) {
        case "likes":
            return active ? "heart.fill" : "heart"
        case "reposts":
            return active ? "arrowshape.turn.up.forward.fill" : "arrowshape.turn.up.forward"
        case "replies":
            return active ? "bubble.left.fill" : "bubble.left"
        default:
            return active ? "questionmark.circle.fill" : "questionmark.circle"
        }
    }
    
    func counterToColor(active: Bool) -> Color {
        switch (self) {
        case "likes":
            return active ? Color("LikedColor") : .secondary
        case "reposts":
            return active ? Color("RepostedColor") : .secondary
        default:
            return .secondary
        }
    }
    
}
