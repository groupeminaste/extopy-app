//
//  TimelineUserView.swift
//  iosApp
//
//  Created by Nathan Fallet on 19/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import Kingfisher
import shared

struct UserCard: View {
    
    let user: Extopy_commonsUser
    let viewedBy: Extopy_commonsUser?
    let counterClick: (Extopy_commonsUser, String) -> Void
    let buttonClick: (Extopy_commonsUser, String) -> Void
    
    @State var userShown = false
    
    var body: some View {
        ZStack {
            /*
            NavigationLink(
                destination: TimelineView(viewModel: TimelineViewModel(
                    type: .user(account: user.account, id: user.id)
                )),
                isActive: $userShown
            ) {
                EmptyView()
            }
            */
            VStack(alignment: .leading, spacing: 12) {
                HStack(alignment: .top) {
                    UserHeaderView(user: user)
                    .onTapGesture {
                        userShown = true
                    }
                    Spacer()
                }
                
                Text(user.biography ?? "")
                
                HStack {
                    Spacer()
                    /*
                    ForEach(user.counters, id: \.id) { counter in
                        Button(action: {
                            counterClick(user, counter.id)
                        }) {
                            Text("timeline_info_\(counter.id)".localized().format(counter.value.simplify()))
                        }
                        .foregroundColor(.primary)
                        Spacer()
                    }
                    */
                }
                HStack {
                    Button(textButton1.localized()) {
                        buttonClick(user, textButton1)
                    }
                    .buttonStyle(filled: filledButton1)
                    Button(textButton2.localized()) {
                        buttonClick(user, textButton2)
                    }
                    .buttonStyle(filled: filledButton2)
                }
            }
            .cardView()
        }
    }
    
    var textButton1: String {
        user.id == viewedBy?.id ? "timeline_button_edit" :
        user.followersIn == true ? "timeline_button_following" :
        "timeline_button_follow"
    }
    
    var filledButton1: Bool {
        textButton1 == "timeline_button_following" || textButton1 == "timeline_button_asked"
    }
    
    var textButton2: String {
        user.id == viewedBy?.id ? "timeline_button_settings" : "timeline_button_dc"
    }
    
    var filledButton2: Bool {
        false
    }
    
}
