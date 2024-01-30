package me.nathanfallet.extopy.repositories.posts

import kotlinx.datetime.Instant
import me.nathanfallet.extopy.models.posts.Post

interface IPostsRepository {

    fun save(post: Post, expiresFromCacheAt: Instant)
    fun get(id: String): Post?
    fun delete(id: String)
    fun deleteAll()
    fun deleteExpired()

}
