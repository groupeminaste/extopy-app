//
//  UserButtonView.swift
//  Extopy
//
//  Created by Nathan Fallet on 13/01/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct UserButtonView: View {
    
    let type: UserButton
    let filled: Bool
    let onClick: () -> Void
    
    var body: some View {
        Button(stringForButton.localized(), action: onClick)
            .buttonStyle(filled: filled)
    }
    
    var stringForButton: String {
        switch (type) {
        case .edit:
            return "timeline_button_edit"
        case .follow:
            return"timeline_button_follow"
        case .following:
            return "timeline_button_following"
        case .asked:
            return "timeline_button_asked"
        case .settings:
            return "timeline_button_settings"
        case .dc:
            return "timeline_button_dc"
        default:
            fatalError()
        }
    }
    
}
