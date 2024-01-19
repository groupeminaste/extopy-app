package me.nathanfallet.extopy.features

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import me.nathanfallet.extopy.features.root.RootView
import me.nathanfallet.extopy.ui.theme.ExtopyTheme

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
