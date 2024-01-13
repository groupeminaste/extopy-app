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
    let onPostsClicked: (Extopy_commonsUser) -> Void
    let onFollowersClicked: (Extopy_commonsUser) -> Void
    let onFollowingClicked: (Extopy_commonsUser) -> Void
    let onEditClicked: (Extopy_commonsUser) -> Void
    let onFollowClicked: (Extopy_commonsUser) -> Void
    let onSettingsClicked: (Extopy_commonsUser) -> Void
    let onDirectMessageClicked: (Extopy_commonsUser) -> Void
    
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
                    UserCounterView(
                        type: .posts,
                        value: user.postsCount?.int64Value ?? 0,
                        onClick: { onPostsClicked(user) }
                    )
                    Spacer()
                    UserCounterView(
                        type: .followers,
                        value: user.followersCount?.int64Value ?? 0,
                        onClick: { onFollowersClicked(user) }
                    )
                    Spacer()
                    UserCounterView(
                        type: .following,
                        value: user.followingCount?.int64Value ?? 0,
                        onClick: { onFollowingClicked(user) }
                    )
                    Spacer()
                }
                HStack {
                    UserButtonView(
                        type: button1,
                        filled: filledButton1,
                        onClick: {
                            button1 == .edit ? onEditClicked(user) : onFollowClicked(user)
                        }
                    )
                    UserButtonView(
                        type: button2,
                        filled: false,
                        onClick: {
                            button2 == .settings ? onSettingsClicked(user) : onDirectMessageClicked(user)
                        }
                    )
                }
            }
            .cardView()
        }
    }
    
    var button1: UserButton {
        user.id == viewedBy?.id ? .edit :
        user.followersIn == true ? .following :
            .follow
    }
    
    var filledButton1: Bool {
        button1 == .following || button1 == .asked
    }
    
    var button2: UserButton {
        user.id == viewedBy?.id ? .settings : .dc
    }
    
}
