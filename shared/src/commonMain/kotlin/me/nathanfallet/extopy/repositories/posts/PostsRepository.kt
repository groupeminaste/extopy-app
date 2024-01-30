package me.nathanfallet.extopy.repositories.posts

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import me.nathanfallet.extopy.database.Database
import me.nathanfallet.extopy.database.Posts
import me.nathanfallet.extopy.models.posts.Post
import me.nathanfallet.extopy.repositories.users.IUsersRepository

class PostsRepository(
    private val database: Database,
    private val usersRepository: IUsersRepository,
) : IPostsRepository {

    override fun save(post: Post, expiresFromCacheAt: Instant) =
        database.postsQueries.save(
            Posts(
                id = post.id,
                userId = post.userId,
                body = post.body,
                expiresFromCacheAt = expiresFromCacheAt.toString()
            )
        )

    override fun get(id: String): Post? =
        database.postsQueries.get(id, Clock.System.now().toString()).executeAsOneOrNull()?.let {
            Post(
                id = it.id,
                userId = it.userId,
                body = it.body,
                user = it.userId?.let { userId -> usersRepository.get(userId) },
            )
        }

    override fun delete(id: String) =
        database.postsQueries.delete(id)

    override fun deleteAll() =
        database.postsQueries.deleteAll()

    override fun deleteExpired() =
        database.postsQueries.deleteExpired(Clock.System.now().toString())

}
