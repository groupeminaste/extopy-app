package com.extopy.domain.usecases.posts

import com.extopy.domain.models.posts.Post
import com.extopy.domain.repositories.PostsRepository
import com.extopy.domain.repositories.SessionRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Instant
import kotlin.uuid.Uuid

class ToggleLikeUseCaseTest {

    private val postId = Uuid.parse("11111111-1111-1111-1111-111111111111")
    private val userId = Uuid.parse("22222222-2222-2222-2222-222222222222")

    private val post = Post(
        id = postId,
        userId = userId,
        publishedAt = Instant.fromEpochMilliseconds(0),
    )

    @Test
    fun likesAPostThatWasNotLiked() = runTest {
        val posts = RecordingPostsRepository()
        val result = ToggleLikeUseCase(posts, session(userId))(post.copy(likesIn = false, likesCount = 4))

        assertEquals(listOf(postId to "like"), posts.calls)
        assertEquals(true, result?.likesIn)
        assertEquals(5, result?.likesCount)
    }

    @Test
    fun unlikesAPostThatWasLiked() = runTest {
        val posts = RecordingPostsRepository()
        val result = ToggleLikeUseCase(posts, session(userId))(post.copy(likesIn = true, likesCount = 4))

        assertEquals(listOf(postId to "unlike"), posts.calls)
        assertEquals(false, result?.likesIn)
        assertEquals(3, result?.likesCount)
    }

    @Test
    fun treatsAnUnknownLikeStateAsNotLiked() = runTest {
        val posts = RecordingPostsRepository()
        val result = ToggleLikeUseCase(posts, session(userId))(post.copy(likesIn = null, likesCount = null))

        assertEquals(listOf(postId to "like"), posts.calls)
        assertEquals(true, result?.likesIn)
        assertEquals(1, result?.likesCount)
    }

    @Test
    fun doesNothingWhenNobodyIsSignedIn() = runTest {
        val posts = RecordingPostsRepository()
        val result = ToggleLikeUseCase(posts, session(null))(post)

        assertTrue(posts.calls.isEmpty())
        assertNull(result)
    }

    private fun session(userId: Uuid?) = object : SessionRepository {
        override fun getUserId() = userId
        override fun setUserId(userId: Uuid?) = Unit
    }

    /** Records which write was asked for, since that is the only thing the use case decides. */
    private class RecordingPostsRepository : PostsRepository {

        val calls = mutableListOf<Pair<Uuid, String>>()

        override suspend fun like(id: Uuid) {
            calls += id to "like"
        }

        override suspend fun unlike(id: Uuid, userId: Uuid) {
            calls += id to "unlike"
        }

        override suspend fun list(limit: Long?, offset: Long?, search: String?) = emptyList<Post>()
        override suspend fun get(id: Uuid): Post? = null
        override suspend fun create(body: String, repliedToId: Uuid?, repostOfId: Uuid?) =
            throw UnsupportedOperationException()

        override suspend fun listReplies(id: Uuid, limit: Long?, offset: Long?) = emptyList<Post>()

    }

}
