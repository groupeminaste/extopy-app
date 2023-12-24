//
//  ViewExtension.swift
//  Extopy
//
//  Created by Nathan Fallet on 09/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

extension View {
    
    func cardView<S: ShapeStyle>(background: S) -> some View {
        self
            .padding()
            .background(
                RoundedRectangle(cornerRadius: 10)
                    .fill(background)
                    .shadow(radius: 1)
            )
    }
    
    func cardView() -> some View {
        self.cardView(background: Color("CardColor"))
    }
    
    func buttonStyle(filled: Bool = true) -> some View {
        self.padding(.horizontal)
            .padding(.vertical, 8)
            .frame(maxWidth: .infinity)
            .foregroundColor(filled ? .white : .accentColor)
            .background(RoundedRectangle(cornerRadius: 10)
                .strokeBorder(Color.accentColor, lineWidth: 1)
                .background(filled ? Color.accentColor : Color.clear)
            )
            .cornerRadius(10)
    }
    
}
