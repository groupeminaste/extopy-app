package com.extopy.data.mappers

import com.extopy.domain.models.posts.Post
import com.extopy.domain.models.users.User
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Instant
import kotlin.uuid.Uuid

class CacheMappersTest {

    private val expiresAt = Instant.fromEpochMilliseconds(60_000)

    @Test
    fun aUserSurvivesARoundTripThroughTheCache() {
        val user = User(
            id = Uuid.parse("22222222-2222-2222-2222-222222222222"),
            displayName = "Nathan",
            username = "nathan",
            biography = "Hello",
            avatar = "https://extopy.com/avatar.png",
            joinDate = Instant.fromEpochMilliseconds(1_000),
            verified = true,
            postsCount = 12,
            followersCount = 34,
            followingCount = 56,
            followersIn = true,
            followingIn = false,
        )

        assertEquals(user, user.toEntity(expiresAt).toDomain())
    }

    @Test
    fun aPostSurvivesARoundTripThroughTheCache() {
        val post = Post(
            id = Uuid.parse("11111111-1111-1111-1111-111111111111"),
            userId = Uuid.parse("22222222-2222-2222-2222-222222222222"),
            repliedToId = Uuid.parse("33333333-3333-3333-3333-333333333333"),
            body = "Hello",
            publishedAt = Instant.fromEpochMilliseconds(1_000),
            editedAt = Instant.fromEpochMilliseconds(2_000),
            likesCount = 3,
            repliesCount = 2,
            repostsCount = 1,
            likesIn = true,
        )

        // The author is stored as a row of its own, so it comes back separately.
        assertEquals(post, post.toEntity(expiresAt).toDomain(user = null))
    }

}
