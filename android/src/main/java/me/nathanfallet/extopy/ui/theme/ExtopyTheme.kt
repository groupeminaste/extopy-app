package me.nathanfallet.extopy.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

val primaryColor = Color(0xFFD9534F)
val primaryVariantColor = Color(0xFFA50000)
val secondaryColor = Color(0xFF03DAC5)

@Composable
fun ExtopyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val darkColorScheme = darkColorScheme(
        primary = primaryColor,
        secondary = secondaryColor,
    )
    val lightColorScheme = lightColorScheme(
        primary = primaryColor,
        secondary = secondaryColor,
    )

    val dynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val colorScheme = when {
        dynamicColor && darkTheme -> dynamicDarkColorScheme(LocalContext.current)
        dynamicColor && !darkTheme -> dynamicLightColorScheme(LocalContext.current)
        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }

    val shapes = Shapes(
        small = RoundedCornerShape(10.dp),
        medium = RoundedCornerShape(10.dp),
        large = RoundedCornerShape(10.dp)
    )

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = shapes,
        content = content
    )
}

val likedColor = Color(0xFFD9534F)
val repostedColor = Color(0xFF008000)
