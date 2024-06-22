//
//  KoinExtension.swift
//  Extopy
//
//  Created by Nathan Fallet on 22/12/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

typealias KoinApplication = Koin_coreKoinApplication
typealias Koin = Koin_coreKoin

extension KoinApplication {
    
    static let shared = companion.start()
    
    @discardableResult
    static func start() -> KoinApplication {
        SwiftModule.shared.environment = environment
        SwiftModule.shared.tokenRepository = TokenRepository()
        return shared
    }
    
    private static var environment: CommonsExtopyEnvironment {
        Bundle.main.bundleIdentifier?.hasSuffix(".dev") == true ?
            CommonsExtopyEnvironment.development :
            CommonsExtopyEnvironment.production
    }
    
    private static let keyPaths: [PartialKeyPath<Koin>] = [
        \.rootViewModel,
         \.searchViewModel,
         \.authViewModel
    ]
    
    func inject<T>() -> T {
        for partialKeyPath in Self.keyPaths {
            guard let keyPath = partialKeyPath as? KeyPath<Koin, T> else { continue }
            return koin[keyPath: keyPath]
        }
        fatalError("\(T.self) is not registered with KoinApplication")
    }
    
}
