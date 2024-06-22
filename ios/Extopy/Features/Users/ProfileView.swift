//
//  ProfileView.swift
//  Extopy
//
//  Created by Nathan Fallet on 13/01/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import shared
import SwiftUI
import Kingfisher
import KMPObservableViewModelSwiftUI
import KMPNativeCoroutinesAsync

struct ProfileView: View {
    
    @StateViewModel var viewModel: ProfileViewModel
    
    let viewedBy: CommonsUser
    
    var body: some View {
        ZStack {
            ScrollView {
                LazyVStack(spacing: 8) {
                    if let user = viewModel.user {
                        NavigationLink(destination: ProfileView(
                            viewModel: KoinApplication.shared.koin.profileViewModel(id: user.id),
                            viewedBy: viewedBy
                        )) {
                            UserCard(
                                user: user,
                                viewedBy: viewedBy,
                                onPostsClicked: { _ in },
                                onFollowersClicked: { _ in },
                                onFollowingClicked: { _ in },
                                onEditClicked: { _ in },
                                onFollowClicked: { _ in
                                    Task {
                                        try await asyncFunction(for: viewModel.onFollowClicked())
                                    }
                                },
                                onSettingsClicked: { _ in },
                                onDirectMessageClicked: { _ in }
                            )
                        }
                    }
                    ForEach(viewModel.posts ?? [], id: \.namespacedId) { post in
                        NavigationLink(destination: PostView(
                            viewModel: KoinApplication.shared.koin.postViewModel(id: post.id),
                            viewedBy: viewedBy
                        )) {
                            PostCard(
                                post: post,
                                viewedBy: viewedBy,
                                onLikeClicked: { post in
                                    Task {
                                        try await asyncFunction(for: viewModel.onLikeClicked(post: post))
                                    }
                                },
                                onRepostClicked: { _ in },
                                onReplyClicked: { _ in }
                            )
                            .onAppear {
                                viewModel.loadMoreIfNeeded(postId: post.id)
                            }
                        }
                    }
                }
                .padding()
            }
        }
        .navigationTitle("timeline_user_title")
        .refreshable {
            Task {
                try await asyncFunction(for: viewModel.fetchUser())
            }
        }
        .onAppear {
            Task {
                try await asyncFunction(for: viewModel.fetchUser())
            }
        }
    }
    
}
