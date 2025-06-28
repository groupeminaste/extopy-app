package com.extopy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.extopy.ui.screens.root.RootView
import com.extopy.ui.theme.ExtopyTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExtopyTheme {
                RootView()
            }
        }
    }

}
