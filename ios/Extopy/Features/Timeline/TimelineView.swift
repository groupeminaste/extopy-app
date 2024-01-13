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
                    ForEach(viewModel.timeline?.users ?? [], id: \.namespacedId) { user in
                        UserCard(
                            user: user,
                            viewedBy: nil,
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
                    ForEach(viewModel.timeline?.posts ?? [], id: \.namespacedId) { post in
                        PostCard(
                            post: post,
                            onLikeClicked: { post in
                                Task {
                                    try await asyncFunction(for: viewModel.onLikeClicked(post: post))
                                }
                            },
                            onRepostClicked: { _ in },
                            onReplyClicked: { _ in }
                        )
                        .onAppear {
                            // TODO: Load more
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
