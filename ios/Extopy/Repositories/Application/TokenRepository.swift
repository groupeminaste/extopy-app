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

    func getRefreshToken() -> String? {
        keychain.value(forKey: "refreshToken") as? String
    }
    
    func getUserId() -> CoreUUID? {
        if let uuid = keychain.value(forKey: "userId") as? UUID {
            CoreUUID(nsUUID: uuid)
        } else {
            nil
        }
    }
    
    func setToken(token: String?) {
        let _ = if let token {
            keychain.save(token, forKey: "token")
        } else {
            keychain.remove(forKey: "token")
        }
    }

    func setRefreshToken(token: String?) {
        let _ = if let token {
            keychain.save(token, forKey: "refreshToken")
        } else {
            keychain.remove(forKey: "refreshToken")
        }
    }

    func setUserId(userId: CoreUUID?) {
        let _ = if let userId {
            keychain.save(userId.nsUUID, forKey: "userId")
        } else {
            keychain.remove(forKey: "userId")
        }
    }
    
}
