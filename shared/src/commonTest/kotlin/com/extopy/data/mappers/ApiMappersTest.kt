package com.extopy.data.mappers

import com.extopy.api.responses.posts.PostResponse
import com.extopy.api.responses.users.UserResponse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.time.Instant
import kotlin.uuid.Uuid

class ApiMappersTest {

    private val userId = Uuid.parse("22222222-2222-2222-2222-222222222222")
    private val postId = Uuid.parse("11111111-1111-1111-1111-111111111111")

    @Test
    fun carriesTheAuthorAlongWithThePost() {
        val post = PostResponse(
            id = postId,
            userId = userId,
            user = UserResponse(id = userId, displayName = "Nathan", username = "nathan"),
            publishedAt = Instant.fromEpochMilliseconds(1_000),
        ).toDomain()

        assertEquals(postId, post.id)
        assertEquals("nathan", post.user?.username)
    }

    @Test
    fun keepsAMissingCounterMissing() {
        // Null and zero are different answers: one means "the endpoint did not return this", the
        // other means "nobody liked it". Turning the first into the second would have the UI draw a
        // count it was never told.
        val post = PostResponse(
            id = postId,
            userId = userId,
            publishedAt = Instant.fromEpochMilliseconds(0),
        ).toDomain()

        assertNull(post.likesCount)
        assertNull(post.user)
    }

    @Test
    fun keepsTheViewerRelativeFlags() {
        val user = UserResponse(
            id = userId,
            displayName = "Nathan",
            username = "nathan",
            followersIn = true,
            followingIn = false,
        ).toDomain()

        assertEquals(true, user.followersIn)
        assertEquals(false, user.followingIn)
    }

}
