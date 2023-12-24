//
//  NumbersExtension.swift
//  iosApp
//
//  Created by Nathan Fallet on 20/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

extension Int64 {
    
    func simplify() -> String {
        let value = Double(self)
        
        if value >= 1_000_000_000 {
            return "\((value / 1_000_000_000).simplify())G"
        }
        
        if value >= 1_000_000 {
            return "\((value / 1_000_000).simplify())M"
        }
        
        if value >= 1_000 {
            return "\((value / 1_000).simplify())K"
        }
        
        return value.simplify()
    }
    
}

extension Double {
    
    func oneDecimal() -> Double {
        return ((self * 10).rounded() / 10)
    }
    
    func simplify() -> String {
        let oneDecimal = self.oneDecimal()
        if floor(oneDecimal) == oneDecimal {
            return String.localizedStringWithFormat("%d", Int(oneDecimal))
        }
        return String.localizedStringWithFormat("%.1f", oneDecimal)
    }
    
}
