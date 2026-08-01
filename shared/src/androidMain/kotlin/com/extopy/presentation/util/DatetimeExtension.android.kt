package com.extopy.presentation.util

import kotlin.time.Instant

actual val Instant.timeAgo: String
    get() = toString()
