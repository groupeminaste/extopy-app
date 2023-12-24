//
//  TokenRepository.swift
//  Extopy
//
//  Created by Nathan Fallet on 22/12/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import Keychain
import shared

class TokenRepository: ITokenRepository {
    
    private let keychain = Keychain()
    
    func getToken() -> String? {
        keychain.value(forKey: "token") as? String
    }
    
    func getUserId() -> String? {
        keychain.value(forKey: "userId") as? String
    }
    
    func setToken(token: String?) {
        let _ = if let token {
            keychain.save(token, forKey: "token")
        } else {
            keychain.remove(forKey: "token")
        }
    }
    
    func setUserId(userId: String?) {
        let _ = if let userId {
            keychain.save(userId, forKey: "userId")
        } else {
            keychain.remove(forKey: "userId")
        }
    }
    
}
