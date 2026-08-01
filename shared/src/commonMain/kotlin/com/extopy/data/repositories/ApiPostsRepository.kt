package com.extopy.data.repositories

import com.extopy.api.requests.posts.PostRequest
import com.extopy.client.ExtopyClient
import com.extopy.data.api.api
import com.extopy.data.api.apiOrNull
import com.extopy.data.database.daos.PostDao
import com.extopy.data.database.daos.UserDao
import com.extopy.data.mappers.toDomain
import com.extopy.domain.models.posts.Post
import com.extopy.domain.repositories.PostsRepository
import kotlin.time.Clock
import kotlin.uuid.Uuid

/**
 * Reads posts from the API, keeping what it read in the local cache.
 *
 * As with users, only [get] is served from the cache — the listings are what the timelines are
 * built from, and serving those from a cache would be showing the user yesterday's feed.
 */
class ApiPostsRepository(
    private val client: ExtopyClient,
    private val posts: PostDao,
    private val users: UserDao,
) : PostsRepository {

    override suspend fun list(limit: Long?, offset: Long?, search: String?): List<Post> =
        api { client.posts.list(limit, offset, search) }
            .map { it.toDomain() }
            .onEach { posts.cache(it, users) }

    override suspend fun get(id: Uuid): Post? {
        posts.get(id.toString(), Clock.System.now().toEpochMilliseconds())?.let { cached ->
            val author = users.get(cached.userId, Clock.System.now().toEpochMilliseconds())
            return cached.toDomain(author?.toDomain())
        }
        return apiOrNull { client.posts.get(id) }?.toDomain()?.also { posts.cache(it, users) }
    }

    override suspend fun create(body: String, repliedToId: Uuid?, repostOfId: Uuid?): Post =
        api { client.posts.create(PostRequest(body, repliedToId, repostOfId)) }
            .toDomain()
            .also { posts.cache(it, users) }

    override suspend fun listReplies(id: Uuid, limit: Long?, offset: Long?): List<Post> =
        api { client.posts.listReplies(id, limit, offset) }
            .map { it.toDomain() }
            .onEach { posts.cache(it, users) }

    override suspend fun like(id: Uuid) {
        api { client.likesInPosts.create(id) }
    }

    override suspend fun unlike(id: Uuid, userId: Uuid) {
        api { client.likesInPosts.delete(id, userId) }
    }

}
