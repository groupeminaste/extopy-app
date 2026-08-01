package com.extopy.domain.models.posts

import com.extopy.domain.models.users.User
import kotlin.time.Instant
import kotlin.uuid.Uuid

/**
 * A post, as the app knows one.
 *
 * [user] is filled in when the endpoint returned the author alongside the post, which is the case
 * for every listing the timelines are built from.
 */
data class Post(
    val id: Uuid,
    val userId: Uuid,
    val user: User? = null,
    val repliedToId: Uuid? = null,
    val repostOfId: Uuid? = null,
    val body: String? = null,
    val publishedAt: Instant,
    val editedAt: Instant? = null,
    val likesCount: Long? = null,
    val repliesCount: Long? = null,
    val repostsCount: Long? = null,
    /** Whether the current user liked this post. */
    val likesIn: Boolean? = null,
)
