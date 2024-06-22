//
//  InjectStateViewModel.swift
//  Extopy
//
//  Created by Nathan Fallet on 22/12/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import KMPObservableViewModelCore
import KMPObservableViewModelSwiftUI

@propertyWrapper
struct InjectStateViewModel<ViewModelType: ViewModel>: DynamicProperty {
    
    @StateViewModel private var viewModel: ViewModelType
    
    var wrappedValue: ViewModelType {
        _viewModel.wrappedValue
    }
    
    var projectedValue: ObservableViewModel<ViewModelType>.Projection {
        _viewModel.projectedValue
    }
    
    init() {
        self._viewModel = StateViewModel(wrappedValue: KoinApplication.shared.inject())
    }
    
}
