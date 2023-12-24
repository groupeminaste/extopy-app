//
//  NamespaceExtension.swift
//  iosApp
//
//  Created by Nathan Fallet on 21/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

extension Extopy_commonsUser {
    var namespacedId: String { "users/\(id)" }
}

extension Extopy_commonsPost {
    var namespacedId: String { "posts/\(id)" }
}

extension Extopy_commonsNotification {
    var namespacedId: String { "notifications/\(id)" }
}
