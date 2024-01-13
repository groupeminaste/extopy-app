//
//  UserCounterView.swift
//  Extopy
//
//  Created by Nathan Fallet on 13/01/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct UserCounterView: View {
    
    let type: UserCounter
    let value: Int64
    let onClick: () -> Void
    
    var body: some View {
        Button(action: onClick) {
            Text(stringForCounter.localized().format(value.simplify()))
        }
    }
    
    var stringForCounter: String {
        switch (type) {
        case .posts:
            return "timeline_info_posts"
        case .followers:
            return"timeline_info_followers"
        case .following:
            return "timeline_info_following"
        default:
            fatalError()
        }
    }
    
}
