package com.extopy.domain.models.users

import kotlin.time.Instant
import kotlin.uuid.Uuid

/**
 * A user, as the app knows one.
 *
 * Everything but the identity is nullable: which fields come back depends on the endpoint the user
 * was read through, and a missing counter is not the same thing as a counter at zero.
 */
data class User(
    val id: Uuid,
    val displayName: String,
    val username: String,
    val biography: String? = null,
    val avatar: String? = null,
    val joinDate: Instant? = null,
    val verified: Boolean? = null,
    val postsCount: Long? = null,
    val followersCount: Long? = null,
    val followingCount: Long? = null,
    /** Whether the current user follows this one. */
    val followersIn: Boolean? = null,
    /** Whether this user follows the current one. */
    val followingIn: Boolean? = null,
)
