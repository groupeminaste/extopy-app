//
//  RootView.swift
//  Extopy
//
//  Created by Nathan Fallet on 09/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared
import KMMViewModelSwiftUI
import KMPNativeCoroutinesAsync

struct RootView: View {
    
    @InjectStateViewModel private var viewModel: RootViewModel
    
    var body: some View {
        Group {
            if (viewModel.user != nil) {
                tabView
            } else {
                AuthView()
            }
        }
        .onAppear {
            Task {
                try await asyncFunction(for: viewModel.fetchUser())
            }
        }
    }
    
    var tabView: some View {
        TabView {
            NavigationView {
                TimelineView(viewModel: KoinApplication.shared.koin.timelineViewModel(id: "default"))
            }
            .tabItem {
                Label("timeline_title", systemImage: "list.bullet")
            }
            NavigationView {
                
            }
            .tabItem {
                Label("direct_message_title", systemImage: "envelope")
            }
            NavigationView {
                NotificationsView(viewModel: NotificationsViewModel())
            }
            .tabItem {
                Label("notifications_title", systemImage: "bell")
            }
            NavigationView {
                SettingsView(viewModel: SettingsViewModel())
            }
            .tabItem {
                Label("settings_title", systemImage: "gearshape")
            }
        }
    }
    
}

struct RootView_Previews: PreviewProvider {
    
    static var previews: some View {
        RootView()
    }
    
}
