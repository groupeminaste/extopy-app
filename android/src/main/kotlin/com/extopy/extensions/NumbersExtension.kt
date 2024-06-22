package com.extopy.extensions

import java.util.*
import kotlin.math.floor

fun Long.simplify(): String {
    val value = toDouble()

    if (value >= 1_000_000_000) {
        return "${(value / 1_000_000_000).simplify()}G"
    }

    if (value >= 1_000_000) {
        return "${(value / 1_000_000).simplify()}M"
    }

    if (value >= 1_000) {
        return "${(value / 1_000).simplify()}K"
    }

    return value.simplify()
}

fun Double.oneDecimal(): Double {
    return (this * 10).toInt() / 10.0
}

fun Double.simplify(): String {
    val onDecimal = oneDecimal()
    if (floor(onDecimal) == onDecimal) {
        return String.format(Locale.getDefault(), "%d", onDecimal.toInt())
    }
    return String.format(Locale.getDefault(), "%.1f", onDecimal)
}
