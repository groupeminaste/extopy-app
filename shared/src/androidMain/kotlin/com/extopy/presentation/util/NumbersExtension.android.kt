package com.extopy.presentation.util

import java.util.Locale
import kotlin.math.floor

actual fun Double.simplify(): String {
    val oneDecimal = oneDecimal()
    return if (floor(oneDecimal) == oneDecimal) String.format(Locale.getDefault(), "%d", oneDecimal.toInt())
    else String.format(Locale.getDefault(), "%.1f", oneDecimal)
}
