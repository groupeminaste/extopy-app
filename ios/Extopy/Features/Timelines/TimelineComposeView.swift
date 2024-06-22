//
//  TimelineComposeView.swift
//  Extopy
//
//  Created by Nathan Fallet on 11/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared
import KMPObservableViewModelSwiftUI
import KMPNativeCoroutinesAsync

struct TimelineComposeView: View {
    
    @StateViewModel var viewModel: TimelineComposeViewModel
    
    let onPostComposed: () -> Void
    
    var body: some View {
        NavigationView {
            Form {
                Section(header: Text("timeline_compose_content")) {
                    TextEditor(text: Binding(get: { viewModel.body }, set: { viewModel.updateBody(value: $0) }))
                }
                Section {
                    Button("timeline_compose_send") {
                        Task {
                            try await asyncFunction(for: viewModel.send())
                            onPostComposed()
                        }
                    }
                    .disabled(viewModel.body.isEmpty)
                }
            }
            .navigationTitle(title.localized())
        }
    }
    
    var title: String {
        viewModel.repliedToId != nil ? "timeline_compose_reply_title" :
        viewModel.repostOfId != nil ? "timeline_compose_repost_title" :
        "timeline_compose_title"
    }
    
}
