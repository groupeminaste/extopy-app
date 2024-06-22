//
//  NotificationsView.swift
//  Extopy
//
//  Created by Nathan Fallet on 20/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import shared
import SwiftUI
import KMPObservableViewModelSwiftUI
import KMPNativeCoroutinesAsync

struct NotificationsView: View {
    
    @StateViewModel var viewModel: NotificationsViewModel
    
    var body: some View {
        List {
            ForEach(viewModel.notifications, id: \.namespacedId) { notification in
                Text(notification.body?.localized() ?? "-")
            }
        }
        .navigationTitle("notifications_title")
    }
    
}

struct NotificationsView_Previews: PreviewProvider {
    
    static var previews: some View {
        NotificationsView(viewModel: NotificationsViewModel())
    }
    
}
