//
//  TimelineView.swift
//  Extopy
//
//  Created by Nathan Fallet on 09/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import shared
import SwiftUI
import Kingfisher
import KMMViewModelSwiftUI
import KMPNativeCoroutinesAsync

struct TimelineView: View {
    
    @StateViewModel var viewModel: TimelineViewModel
    
    @State var sheet: TimelineSheet?
    
    let viewedBy: Extopy_commonsUser
    
    var body: some View {
        ZStack {
            /*
            NavigationLink(
                destination: TimelineView(viewModel: TimelineViewModel(
                    type: .search(search: viewModel.searchText)
                )),
                isActive: $viewModel.searchShown
            ) {
                EmptyView()
            }
            */
            ScrollView {
                LazyVStack(spacing: 8) {
                    ForEach(viewModel.users ?? [], id: \.namespacedId) { user in
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
                                onFollowClicked: { user in
                                    Task {
                                        try await asyncFunction(for: viewModel.onFollowClicked(user: user))
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
        .navigationTitle("timeline_title")
        .refreshable {
            Task {
                try await asyncFunction(for: viewModel.fetchTimeline())
            }
        }
        .toolbar {
            ToolbarItemGroup(placement: .navigationBarTrailing) {
                Button(action: {
                    self.sheet = .compose(repliedToId: nil, repostOfId: nil)
                }) {
                    Image(systemName: "square.and.pencil")
                }
            }
        }
        /*
        .searchable(text: $viewModel.searchText) {
            EmptyView()
        }
        .onSubmit(of: .search, viewModel.submitSearch)
         */
        .sheet(item: $sheet) { sheet in
            switch (sheet) {
            case .compose(let repliedToId, let repostOfId):
                TimelineComposeView(
                    viewModel: KoinApplication.shared.koin.timelineComposeViewModel(
                        body: "",
                        repliedToId: repliedToId,
                        repostOfId: repostOfId
                    ),
                    onPostComposed: {
                        self.sheet = nil
                    }
                )
            case .userEdit(let user):
                TimelineUserEditView(viewModel: TimelineUserEditViewModel(
                    user: user,
                    onUserEdited: { _ in }
                ))
            }
        }
        .onAppear {
            Task {
                try await asyncFunction(for: viewModel.fetchTimeline())
            }
        }
    }
    
}
