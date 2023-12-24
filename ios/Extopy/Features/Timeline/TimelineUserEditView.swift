//
//  TimelineUserEditView.swift
//  iosApp
//
//  Created by Nathan Fallet on 20/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct TimelineUserEditView: View {
    
    @StateObject var viewModel: TimelineUserEditViewModel
    
    var body: some View {
        NavigationView {
            Form {
                Section(header: Text("timeline_user_edit_main")) {
                    TextField("timeline_user_edit_username", text: $viewModel.username)
                    TextField("timeline_user_edit_displayName", text: $viewModel.displayName)
                }
                Section(header: Text("timeline_user_edit_biography")) {
                    TextEditor(text: $viewModel.biography)
                }
                Section {
                    Button("timeline_user_edit_save", action: viewModel.send)
                }
            }
            .navigationTitle("timeline_user_edit_title")
        }
        .onAppear(perform: viewModel.onAppear)
    }
    
}
