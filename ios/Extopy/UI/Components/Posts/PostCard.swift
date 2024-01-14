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
    let viewedBy: Extopy_commonsUser
    let onLikeClicked: (Extopy_commonsPost) -> Void
    let onRepostClicked: (Extopy_commonsPost) -> Void
    let onReplyClicked: (Extopy_commonsPost) -> Void
    
    @State var postShown = false
    
    var body: some View {
        ZStack {
            /*
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
                        NavigationLink(destination: ProfileView(
                            viewModel: KoinApplication.shared.koin.profileViewModel(id: user.id),
                            viewedBy: viewedBy
                        )) {
                            UserHeaderView(user: user)
                                .foregroundColor(.primary)
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
            .foregroundColor(.primary)
            .cardView().onTapGesture {
                postShown = true
            }
        }
    }
    
}
