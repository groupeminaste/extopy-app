//
//  PostView.swift
//  Extopy
//
//  Created by Nathan Fallet on 14/01/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import shared
import SwiftUI
import Kingfisher
import KMMViewModelSwiftUI
import KMPNativeCoroutinesAsync

struct PostView: View {
    
    @StateViewModel var viewModel: PostViewModel
    
    let viewedBy: Extopy_commonsUser
    
    var body: some View {
        ZStack {
            ScrollView {
                LazyVStack(spacing: 8) {
                    if let post = viewModel.post {
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
        .navigationTitle("timeline_post_title")
        .refreshable {
            Task {
                try await asyncFunction(for: viewModel.fetchPost())
            }
        }
        .onAppear {
            Task {
                try await asyncFunction(for: viewModel.fetchPost())
            }
        }
    }
    
}
