//
//  InjectStateViewModel.swift
//  Extopy
//
//  Created by Nathan Fallet on 22/12/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import KMMViewModelCore
import KMMViewModelSwiftUI

@propertyWrapper
struct InjectStateViewModel<ViewModel: KMMViewModel>: DynamicProperty {
    
    @StateViewModel private var viewModel: ViewModel
    
    var wrappedValue: ViewModel {
        _viewModel.wrappedValue
    }
    
    var projectedValue: ObservableViewModel<ViewModel>.Projection {
        _viewModel.projectedValue
    }
    
    init() {
        self._viewModel = StateViewModel(wrappedValue: KoinApplication.shared.inject())
    }
    
}
