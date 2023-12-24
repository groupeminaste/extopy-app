//
//  DatabaseExtension.swift
//  Extopy
//
//  Created by Nathan Fallet on 09/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

extension Database {
    
    static let shared = Database(databaseDriverFactory: DatabaseDriverFactory())
    
}
