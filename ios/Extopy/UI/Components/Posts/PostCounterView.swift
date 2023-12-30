//
//  PostCounterView.swift
//  Extopy
//
//  Created by Nathan Fallet on 29/12/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct PostCounterView: View {
    
    let type: PostCounter
    let value: Int64
    let active: Bool
    let onClick: (PostCounter) -> Void
    
    var body: some View {
        Button(action: { onClick(type) }) {
            Image(systemName: symbolForCounter(type: type, active: active))
                .resizable()
                .frame(width: 16, height: 16)
            Text(value.simplify())
        }
        .foregroundColor(colorForCounter(type: type, active: active) ?? .secondary)
    }
    
    func symbolForCounter(type: PostCounter, active: Bool) -> String {
        switch (type) {
        case .likes:
            return active ? "heart.fill" : "heart"
        case .reposts:
            return active ? "arrowshape.turn.up.forward.fill" : "arrowshape.turn.up.forward"
        case .replies:
            return active ? "bubble.left.fill" : "bubble.left"
        default:
            return active ? "questionmark.circle.fill" : "questionmark.circle"
        }
    }
    
    func colorForCounter(type: PostCounter, active: Bool) -> Color? {
        switch (type) {
        case .likes:
            return active ? Color("LikedColor") : nil
        case .reposts:
            return active ? Color("RepostedColor") : nil
        default:
            return nil
        }
    }
    
}
