package com.extopy

import androidx.compose.runtime.Composable
import com.extopy.presentation.navigation.ExtopyNavHost
import com.extopy.presentation.theme.ExtopyTheme
import org.koin.compose.KoinContext

/** The app, from the theme down. Both platforms mount this and nothing else. */
@Composable
fun App() {
    KoinContext {
        ExtopyTheme {
            ExtopyNavHost()
        }
    }
}
