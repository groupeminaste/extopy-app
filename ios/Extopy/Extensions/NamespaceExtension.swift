//
//  NamespaceExtension.swift
//  iosApp
//
//  Created by Nathan Fallet on 21/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

extension CommonsUser {
    var namespacedId: String { "users/\(id)" }
}

extension CommonsPost {
    var namespacedId: String { "posts/\(id)" }
}

extension CommonsNotification {
    var namespacedId: String { "notifications/\(id)" }
}
