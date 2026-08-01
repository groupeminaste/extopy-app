package com.extopy.data.repositories

import com.extopy.api.requests.posts.PostRequest
import com.extopy.api.responses.posts.LikeInPostResponse
import com.extopy.api.responses.posts.PostResponse
import com.extopy.api.responses.users.UserResponse
import com.extopy.client.ExtopyClient
import com.extopy.client.api.auth.AuthApiClient
import com.extopy.client.api.posts.LikesInPostsApiClient
import com.extopy.client.api.posts.PostsApiClient
import com.extopy.client.api.timelines.TimelinesApiClient
import com.extopy.client.api.users.FollowersInUsersApiClient
import com.extopy.client.api.users.UsersApiClient
import com.extopy.data.database.daos.PostDao
import com.extopy.data.database.daos.UserDao
import com.extopy.data.database.entities.PostEntity
import com.extopy.data.database.entities.UserEntity
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Instant
import kotlin.uuid.Uuid

class ApiPostsRepositoryTest {

    private val postId = Uuid.parse("11111111-1111-1111-1111-111111111111")
    private val userId = Uuid.parse("22222222-2222-2222-2222-222222222222")

    private val response = PostResponse(
        id = postId,
        userId = userId,
        user = UserResponse(id = userId, displayName = "Nathan", username = "nathan"),
        body = "Hello",
        publishedAt = Instant.fromEpochMilliseconds(1_000),
        likesCount = 4,
        likesIn = false,
    )

    @Test
    fun readsBackFromTheCacheRatherThanTheNetwork() = runTest {
        val client = FakeClient(listOf(response))
        val posts = FakePostDao()
        val users = FakeUserDao()
        val repository = ApiPostsRepository(client, posts, users)

        repository.list()
        val read = repository.get(postId)

        assertEquals("Hello", read?.body)
        // The listing was the only call: the second read came out of the cache.
        assertEquals(1, client.posts.listCalls)
        assertEquals(0, client.posts.getCalls)
        // And the author came back with it, from its own row.
        assertEquals("nathan", read?.user?.username)
    }

    @Test
    fun forgetsACachedPostOnceItHasBeenLiked() = runTest {
        val client = FakeClient(listOf(response))
        val posts = FakePostDao()
        val repository = ApiPostsRepository(client, posts, FakeUserDao())

        repository.list()
        repository.like(postId)

        // The like moved a counter the cached row still holds the old value of, so the row has to
        // go — otherwise reopening the post inside the cache lifetime shows it unliked again.
        assertNull(posts.rows[postId.toString()])
    }

    @Test
    fun forgetsACachedPostOnceTheLikeIsTakenBack() = runTest {
        val client = FakeClient(listOf(response))
        val posts = FakePostDao()
        val repository = ApiPostsRepository(client, posts, FakeUserDao())

        repository.list()
        repository.unlike(postId, userId)

        assertNull(posts.rows[postId.toString()])
    }

    @Test
    fun goesToTheNetworkAgainAfterALike() = runTest {
        val client = FakeClient(listOf(response))
        val repository = ApiPostsRepository(client, FakePostDao(), FakeUserDao())

        repository.list()
        repository.like(postId)
        repository.get(postId)

        assertTrue(client.posts.getCalls == 1, "the post should have been read again")
    }

    // Fakes — only what this repository touches is implemented; the rest would be a compile-time
    // requirement of the interface and nothing more.

    private class FakePostDao : PostDao {
        val rows = mutableMapOf<String, PostEntity>()
        override suspend fun save(post: PostEntity) {
            rows[post.id] = post
        }

        override suspend fun get(id: String, now: Long) = rows[id]?.takeIf { it.expiresAt > now }
        override suspend fun delete(id: String) {
            rows.remove(id)
        }

        override suspend fun deleteExpired(now: Long) {
            rows.values.removeAll { it.expiresAt <= now }
        }

        override suspend fun deleteAll() = rows.clear()
    }

    private class FakeUserDao : UserDao {
        val rows = mutableMapOf<String, UserEntity>()
        override suspend fun save(user: UserEntity) {
            rows[user.id] = user
        }

        override suspend fun get(id: String, now: Long) = rows[id]?.takeIf { it.expiresAt > now }
        override suspend fun delete(id: String) {
            rows.remove(id)
        }

        override suspend fun deleteExpired(now: Long) {
            rows.values.removeAll { it.expiresAt <= now }
        }

        override suspend fun deleteAll() = rows.clear()
    }

    private class FakePostsApiClient(private val posts: List<PostResponse>) : PostsApiClient {
        var listCalls = 0
        var getCalls = 0

        override suspend fun list(limit: Long?, offset: Long?, search: String?): List<PostResponse> {
            listCalls++
            return posts
        }

        override suspend fun get(id: Uuid): PostResponse {
            getCalls++
            return posts.first { it.id == id }
        }

        override suspend fun create(request: PostRequest) = posts.first()
        override suspend fun update(id: Uuid, request: PostRequest) = posts.first()
        override suspend fun delete(id: Uuid) = Unit
        override suspend fun listReplies(id: Uuid, limit: Long?, offset: Long?) = emptyList<PostResponse>()
    }

    private class FakeLikesApiClient : LikesInPostsApiClient {
        override suspend fun list(postId: Uuid, limit: Long?, offset: Long?) = emptyList<LikeInPostResponse>()
        override suspend fun create(postId: Uuid) = LikeInPostResponse(postId = postId, userId = Uuid.random())
        override suspend fun delete(postId: Uuid, userId: Uuid) = Unit
    }

    private class FakeClient(responses: List<PostResponse>) : ExtopyClient {
        override val posts = FakePostsApiClient(responses)
        override val likesInPosts = FakeLikesApiClient()
        override val auth: AuthApiClient get() = error("not used")
        override val users: UsersApiClient get() = error("not used")
        override val followersInUsers: FollowersInUsersApiClient get() = error("not used")
        override val timelines: TimelinesApiClient get() = error("not used")
    }

}
