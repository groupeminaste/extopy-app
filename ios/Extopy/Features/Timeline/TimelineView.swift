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
                            counterClick: viewModel.counterClicked,
                            buttonClick: { _, _ in /* viewModel.buttonClicked */ }
                        )
                    }
                    ForEach(viewModel.timeline?.posts ?? [], id: \.namespacedId) { post in
                        PostCard(
                            post: post,
                            counterClick: viewModel.counterClicked
                        )
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
                Button(action: { /*viewModel.showCompose */ }) {
                    Image(systemName: "square.and.pencil")
                }
            }
        }
        /*
        .searchable(text: $viewModel.searchText) {
            EmptyView()
        }
        .onSubmit(of: .search, viewModel.submitSearch)
        .sheet(item: $viewModel.sheet) { sheet in
            switch (sheet) {
            case .compose(let account, let upload):
                TimelineComposeView(viewModel: TimelineComposeViewModel(
                    account: account,
                    upload: upload,
                    onPostComposed: viewModel.onPostComposed
                ))
            case .userEdit(let account, let user):
                TimelineUserEditView(viewModel: TimelineUserEditViewModel(
                    account: account,
                    user: user,
                    onUserEdited: viewModel.onUserEdited
                ))
            }
        }
        */
        .onAppear {
            Task {
                try await asyncFunction(for: viewModel.fetchTimeline())
            }
        }
    }
    
}
