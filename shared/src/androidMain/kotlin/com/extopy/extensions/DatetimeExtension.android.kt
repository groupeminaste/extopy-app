package com.extopy.extensions

import kotlinx.datetime.Instant

actual val Instant.timeAgo: String
    get() = toString()
