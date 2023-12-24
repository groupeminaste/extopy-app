//
//  TimelineUserEditViewModel.swift
//  iosApp
//
//  Created by Nathan Fallet on 20/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

class TimelineUserEditViewModel: ObservableObject {
    
    let onUserEdited: (Extopy_commonsUser) -> Void
    
    @Published var username: String
    @Published var displayName: String
    @Published var biography: String
    
    init(user: Extopy_commonsUser, onUserEdited: @escaping (Extopy_commonsUser) -> Void) {
        self.username = user.username
        self.displayName = user.displayName
        self.biography = user.biography ?? ""
        self.onUserEdited = onUserEdited
    }
    
    func onAppear() {
        
    }
    
    func send() {
        
    }

}
