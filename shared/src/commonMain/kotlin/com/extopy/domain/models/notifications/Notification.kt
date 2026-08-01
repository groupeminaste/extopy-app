package com.extopy.domain.models.notifications

import kotlin.time.Instant
import kotlin.uuid.Uuid

/**
 * A notification, as the app knows one.
 *
 * The API does not expose notifications yet, so nothing produces these for now — the model is here
 * because the screen that renders them is, and it pins the shape we will read once it does.
 */
data class Notification(
    val id: Uuid,
    val body: String? = null,
    val publishedAt: Instant? = null,
)
