package com.extopy.di

import androidx.compose.ui.uikit.OnFocusBehavior
import androidx.compose.ui.window.ComposeUIViewController
import com.extopy.ui.screens.root.RootView
import com.extopy.ui.theme.ExtopyTheme
import platform.UIKit.UIViewController

@Suppress("unused") // Called from Swift
fun MainViewController(): UIViewController = ComposeUIViewController(
    configure = { onFocusBehavior = OnFocusBehavior.DoNothing },
) {
    ExtopyTheme {
        RootView()
    }
}
