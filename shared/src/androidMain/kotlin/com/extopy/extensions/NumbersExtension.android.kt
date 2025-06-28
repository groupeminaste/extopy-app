package com.extopy.extensions

import dev.kaccelero.commons.localization.Locale
import kotlin.math.floor

actual fun Double.simplify(): String {
    val onDecimal = oneDecimal()
    return if (floor(onDecimal) == onDecimal) String.format(Locale.getDefault(), "%d", onDecimal.toInt())
    else String.format(Locale.getDefault(), "%.1f", onDecimal)
}
