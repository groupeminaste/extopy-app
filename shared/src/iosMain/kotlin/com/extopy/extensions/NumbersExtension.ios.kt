package com.extopy.extensions

import platform.Foundation.NSString
import platform.Foundation.localizedStringWithFormat
import kotlin.math.floor

actual fun Double.simplify(): String {
    val oneDecimal = oneDecimal()
    return if (floor(oneDecimal) == oneDecimal) NSString.localizedStringWithFormat("%d", oneDecimal.toInt())
    else NSString.localizedStringWithFormat("%.1f", oneDecimal)
}
