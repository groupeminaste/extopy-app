package com.extopy.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

/**
 * Typography for a feed: the author's name and the post body must never look alike.
 *
 * Everything a post is made of gets its own style below — [AuthorNameStyle], [HandleStyle],
 * [PostBodyStyle], [TimestampStyle] — because leaving them all on the default body style is what
 * made the old cards read as one flat block of text.
 */
val ExtopyTypography: Typography = Typography().run {
    copy(
        headlineLarge = headlineLarge.copy(fontWeight = FontWeight.Bold),
        headlineMedium = headlineMedium.copy(fontWeight = FontWeight.Bold),
        headlineSmall = headlineSmall.copy(fontWeight = FontWeight.SemiBold),
        titleLarge = titleLarge.copy(fontWeight = FontWeight.Bold, letterSpacing = (-0.015).em),
        titleMedium = titleMedium.copy(fontWeight = FontWeight.SemiBold),
        titleSmall = titleSmall.copy(fontWeight = FontWeight.SemiBold),
        labelLarge = labelLarge.copy(fontWeight = FontWeight.SemiBold),
    )
}

/** The author of a post: the anchor of the card, so it is the heaviest thing in it. */
val AuthorNameStyle = TextStyle(
    fontSize = 15.sp,
    lineHeight = 19.sp,
    fontWeight = FontWeight.Bold,
)

/** The `@handle` under the name — present, but never competing with it. */
val HandleStyle = TextStyle(
    fontSize = 13.sp,
    lineHeight = 16.sp,
    fontWeight = FontWeight.Normal,
)

/** The post itself. Slightly taller lines than Material's default, since posts are read, not scanned. */
val PostBodyStyle = TextStyle(
    fontSize = 15.sp,
    lineHeight = 22.sp,
    fontWeight = FontWeight.Normal,
)

/** "2 s", "5 min". Small, quiet, and out of the way. */
val TimestampStyle = TextStyle(
    fontSize = 12.sp,
    lineHeight = 15.sp,
    fontWeight = FontWeight.Normal,
)

/** The number inside a counter bubble. */
val CounterStyle = TextStyle(
    fontSize = 12.sp,
    lineHeight = 15.sp,
    fontWeight = FontWeight.SemiBold,
)
