package com.extopy.presentation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Extopy is one red on neutrals that are tinted with that same red. Nothing here is a pure grey:
// every surface carries a little of the brand, which is what keeps a mostly-monochrome feed from
// looking like a system dialog. The red itself stays saturated and is spent sparingly — a filled
// pill for the current tab, a heart, and little else.

/** The brand red. Fixed by the project: it is the one value in here that is not ours to tune. */
private val Brand = Color(0xFFD9534F)

// Light — a warm off-white ground with white cards on top of it.
private val LightBackground = Color(0xFFF7EFEE)
private val LightSurface = Color(0xFFFFFFFF)
private val LightSurfaceVariant = Color(0xFFF2E6E5)
private val LightOnSurface = Color(0xFF1F1719)
private val LightOnSurfaceVariant = Color(0xFF7A6B6D)
private val LightOutline = Color(0xFFE7DAD9)

// Dark — the same relationship, inverted: cards sit *above* the ground rather than glowing.
private val DarkBackground = Color(0xFF171112)
private val DarkSurface = Color(0xFF241B1C)
private val DarkSurfaceVariant = Color(0xFF2F2324)
private val DarkOnSurface = Color(0xFFF0E9EA)
private val DarkOnSurfaceVariant = Color(0xFF8C7E80)
private val DarkOutline = Color(0xFF3A2B2D)

val LightColors = lightColorScheme(
    primary = Brand,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFBE3E2),
    onPrimaryContainer = Color(0xFF5C1B19),
    secondary = Color(0xFF7A6B6D),
    onSecondary = Color.White,
    background = LightBackground,
    onBackground = LightOnSurface,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnSurfaceVariant,
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFFDF9F8),
    surfaceContainer = Color(0xFFFAF4F3),
    surfaceContainerHigh = Color(0xFFF2E6E5),
    surfaceContainerHighest = Color(0xFFEBDDDC),
    outline = LightOutline,
    outlineVariant = Color(0xFFECE0DF),
    error = Color(0xFFB3261E),
    onError = Color.White,
)

val DarkColors = darkColorScheme(
    primary = Brand,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF4A1A18),
    onPrimaryContainer = Color(0xFFFFD9D7),
    secondary = Color(0xFF8C7E80),
    onSecondary = Color(0xFF1F1719),
    background = DarkBackground,
    onBackground = DarkOnSurface,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,
    surfaceContainerLowest = Color(0xFF120D0E),
    surfaceContainerLow = Color(0xFF1D1516),
    surfaceContainer = Color(0xFF241B1C),
    surfaceContainerHigh = Color(0xFF2F2324),
    surfaceContainerHighest = Color(0xFF3A2B2D),
    outline = DarkOutline,
    outlineVariant = Color(0xFF2A2021),
    error = Color(0xFFF2B8B5),
    onError = Color(0xFF601410),
)

/**
 * The colours Material has no role for.
 *
 * A liked heart and a repost are not "primary" or "error" — they are states of a post, and giving
 * them Material roles would either misname them or collide with the brand. In particular [liked]
 * is deliberately *not* the brand red: a heart that lights up in exactly the accent colour is
 * indistinguishable from the accent itself.
 */
@Immutable
data class ExtopySemanticColors(
    /** A post the reader has liked. A deeper red than the brand, so the two never read as one. */
    val liked: Color,
    /** A post the reader has reposted. Desaturated to sit next to the red without fighting it. */
    val reposted: Color,
    /** The ground of a counter bubble at rest. */
    val bubble: Color,
    /** The ground of a counter bubble once its action is active. */
    val bubbleActive: Color,
)

private val LightSemanticColors = ExtopySemanticColors(
    liked = Color(0xFFC9302C),
    reposted = Color(0xFF4C8B6B),
    bubble = Color(0xFFF2E6E5),
    bubbleActive = Color(0x1FD9534F),
)

private val DarkSemanticColors = ExtopySemanticColors(
    // Lifted on the dark ground, where the light theme's deeper red would disappear.
    liked = Color(0xFFF2635E),
    reposted = Color(0xFF6FB894),
    bubble = Color(0xFF2F2324),
    bubbleActive = Color(0x29E5716D),
)

internal fun semanticColors(darkTheme: Boolean) =
    if (darkTheme) DarkSemanticColors else LightSemanticColors

internal val LocalExtopySemanticColors = staticCompositionLocalOf { LightSemanticColors }
