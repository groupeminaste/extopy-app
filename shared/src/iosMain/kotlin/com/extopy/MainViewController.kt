package com.extopy

import androidx.compose.ui.uikit.OnFocusBehavior
import androidx.compose.ui.window.ComposeUIViewController
import com.extopy.di.initKoin
import com.extopy.di.initializeSentry
import com.extopy.di.platformModule
import platform.UIKit.UIViewController
import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.Platform

private var started = false

/**
 * Wires the app up, once.
 *
 * SwiftUI is free to build the view more than once and starting Koin twice throws, so the guard is
 * what makes this safe to call from `configure` — which is also the earliest point at which the app
 * is definitely going on screen.
 */
@OptIn(ExperimentalNativeApi::class)
private fun startOnce() {
    if (started) return
    started = true
    initKoin(platformModule())
    // A crash while developing is not signal worth sending anywhere.
    if (!Platform.isDebugBinary) initializeSentry()
}

@Suppress("unused") // Called from Swift
fun MainViewController(): UIViewController = ComposeUIViewController(
    configure = {
        onFocusBehavior = OnFocusBehavior.DoNothing
        startOnce()
    },
) {
    App()
}
