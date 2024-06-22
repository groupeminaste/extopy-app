package com.extopy.features

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.extopy.features.root.RootView
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
