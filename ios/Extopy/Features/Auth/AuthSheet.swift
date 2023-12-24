//
//  AuthSheet.swift
//  Extopy
//
//  Created by Nathan Fallet on 10/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

enum AuthSheet: Identifiable {
    
    case safari(url: URL)
    
    var id: String {
        switch (self) {
        case .safari(let url):
            return url.absoluteString
        }
    }
    
}
