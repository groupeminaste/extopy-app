package com.extopy.data.repositories

import com.extopy.client.ExtopyClient
import com.extopy.data.api.api
import com.extopy.data.api.apiOrNull
import com.extopy.data.database.daos.PostDao
import com.extopy.data.database.daos.UserDao
import com.extopy.data.mappers.toDomain
import com.extopy.domain.models.posts.Post
import com.extopy.domain.models.timelines.Timeline
import com.extopy.domain.repositories.TimelinesRepository
import kotlin.uuid.Uuid

/**
 * Reads timelines from the API.
 *
 * A timeline itself is not cached — it is the one thing the app asks the server to recompute — but
 * the posts it hands back are, so opening one of them afterwards is free.
 */
class ApiTimelinesRepository(
    private val client: ExtopyClient,
    private val posts: PostDao,
    private val users: UserDao,
) : TimelinesRepository {

    override suspend fun get(id: Uuid): Timeline? =
        apiOrNull { client.timelines.get(id) }?.toDomain()

    override suspend fun listPosts(id: Uuid, limit: Long?, offset: Long?): List<Post> =
        api { client.timelines.listPosts(id, limit, offset) }
            .map { it.toDomain() }
            .onEach { posts.cache(it, users) }

}
