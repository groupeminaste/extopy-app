package com.extopy.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.dp

/**
 * Generous corners throughout: this is the softest of the shape scales Material allows for, and it
 * is what carries the theme as much as the colours do.
 */
private val ExtopyShapes = Shapes(
    extraSmall = RoundedCornerShape(10.dp),
    small = RoundedCornerShape(14.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(28.dp),
    extraLarge = RoundedCornerShape(36.dp),
)

/** A pill. Used for the search field, the tab bar and the counter bubbles. */
val PillShape = RoundedCornerShape(percent = 50)

@Composable
fun ExtopyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalExtopySemanticColors provides semanticColors(darkTheme)) {
        MaterialTheme(
            colorScheme = if (darkTheme) DarkColors else LightColors,
            typography = ExtopyTypography,
            shapes = ExtopyShapes,
            content = content,
        )
    }
}

/**
 * The parts of the theme Material does not carry.
 *
 * Reads like `MaterialTheme.colorScheme` does — `ExtopyTheme.semantic.liked` — so that nothing in
 * the UI has to know a CompositionLocal is involved.
 */
object ExtopyTheme {
    val semantic: ExtopySemanticColors
        @Composable
        @ReadOnlyComposable
        get() = LocalExtopySemanticColors.current
}

/**
 * Cards get depth from colour rather than from a shadow: [androidx.compose.material3.Surface] sits
 * a step above the background, and nothing is elevated.
 */
@Composable
fun defaultCardColors() = CardDefaults.cardColors(
    containerColor = MaterialTheme.colorScheme.surface,
    contentColor = MaterialTheme.colorScheme.onSurface,
)
