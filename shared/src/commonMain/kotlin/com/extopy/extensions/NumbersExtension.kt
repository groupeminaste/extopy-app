package com.extopy.extensions

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

expect fun Double.simplify(): String
