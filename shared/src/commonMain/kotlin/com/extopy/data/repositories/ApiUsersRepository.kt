package com.extopy.data.repositories

import com.extopy.client.ExtopyClient
import com.extopy.data.api.api
import com.extopy.data.api.apiOrNull
import com.extopy.data.database.daos.PostDao
import com.extopy.data.database.daos.UserDao
import com.extopy.data.mappers.toDomain
import com.extopy.domain.models.posts.Post
import com.extopy.domain.models.users.User
import com.extopy.domain.repositories.UsersRepository
import kotlin.time.Clock
import kotlin.uuid.Uuid

/**
 * Reads users from the API, keeping what it read in the local cache.
 *
 * Only [get] is served from the cache: a listing is a window into something that moves, so re-reading
 * it is the point, whereas opening the same profile twice in a row should not cost two round trips.
 */
class ApiUsersRepository(
    private val client: ExtopyClient,
    private val users: UserDao,
    private val posts: PostDao,
) : UsersRepository {

    override suspend fun list(limit: Long?, offset: Long?, search: String?): List<User> =
        api { client.users.list(limit, offset, search) }
            .map { it.toDomain() }
            .onEach { users.cache(it) }

    override suspend fun get(id: Uuid): User? {
        users.get(id.toString(), Clock.System.now().toEpochMilliseconds())?.let { return it.toDomain() }
        return apiOrNull { client.users.get(id) }?.toDomain()?.also { users.cache(it) }
    }

    override suspend fun listPosts(id: Uuid, limit: Long?, offset: Long?): List<Post> =
        api { client.users.listPosts(id, limit, offset) }
            .map { it.toDomain() }
            .onEach { posts.cache(it, users) }

    override suspend fun follow(id: Uuid) {
        api { client.followersInUsers.create(id) }
    }

    override suspend fun unfollow(id: Uuid, followerId: Uuid) {
        api { client.followersInUsers.delete(id, followerId) }
    }

}
