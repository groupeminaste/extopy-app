//
//  TimelineSheet.swift
//  Extopy
//
//  Created by Nathan Fallet on 11/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

enum TimelineSheet: Identifiable {
    
    case compose(user: Extopy_commonsUser, payload: Extopy_commonsPostPayload?)
    case userEdit(user: Extopy_commonsUser)
    
    var id: String {
        switch (self) {
        case .compose:
            return "compose"
        case .userEdit:
            return "userEdit"
        }
    }
    
}
