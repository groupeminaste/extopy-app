package com.extopy.repositories.posts

import com.extopy.models.posts.Post
import dev.kaccelero.models.UUID
import kotlinx.datetime.Instant

interface IPostsRepository {

    fun save(post: Post, expiresFromCacheAt: Instant)
    fun get(id: UUID): Post?
    fun delete(id: UUID)
    fun deleteAll()
    fun deleteExpired()

}
