//
//  TimelinePostView.swift
//  Extopy
//
//  Created by Nathan Fallet on 09/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import Kingfisher
import shared

struct PostCard: View {
    
    let post: Extopy_commonsPost
    let onLikeClicked: (Extopy_commonsPost) -> Void
    let onRepostClicked: (Extopy_commonsPost) -> Void
    let onReplyClicked: (Extopy_commonsPost) -> Void
    
    @State var userShown = false
    @State var postShown = false
    
    var body: some View {
        ZStack {
            /*
            NavigationLink(
                destination: TimelineView(viewModel: TimelineViewModel(
                    type: .user(account: post.account, id: post.user.id)
                )),
                isActive: $userShown
            ) {
                EmptyView()
            }
            NavigationLink(
                destination: TimelineView(viewModel: TimelineViewModel(
                    type: .post(account: post.account, id: post.id)
                )),
                isActive: $postShown
            ) {
                EmptyView()
            }
            */
            VStack(alignment: .leading, spacing: 12) {
                HStack(alignment: .top) {
                    if let user = post.user {
                        UserHeaderView(user: user)
                            .onTapGesture {
                                userShown = true
                            }
                    }
                    Spacer()
                    Text(post.published?.timeAgo ?? "")
                        .foregroundColor(.secondary)
                }
                
                Text(post.body ?? "")
                
                HStack {
                    Spacer()
                    PostCounterView(
                        type: .likes,
                        value: post.likesCount?.int64Value ?? 0,
                        active: post.likesIn == true,
                        onClick: { onLikeClicked(post) }
                    )
                    Spacer()
                    PostCounterView(
                        type: .reposts,
                        value: post.repostsCount?.int64Value ?? 0,
                        active: false,
                        onClick: { onRepostClicked(post) }
                    )
                    Spacer()
                    PostCounterView(
                        type: .replies,
                        value: post.repliesCount?.int64Value ?? 0,
                        active: false,
                        onClick: { onReplyClicked(post) }
                    )
                    Spacer()
                }
            }
            .cardView().onTapGesture {
                postShown = true
            }
        }
    }
    
}
