package com.extopy.repositories.posts

import com.extopy.database.Database
import com.extopy.database.Posts
import com.extopy.models.posts.Post
import com.extopy.repositories.users.IUsersRepository
import dev.kaccelero.models.UUID
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class PostsRepository(
    private val database: Database,
    private val usersRepository: IUsersRepository,
) : IPostsRepository {

    private fun Posts.toPost() =
        Post(
            id = UUID(id),
            userId = UUID(userId),
            body = body,
            user = usersRepository.get(UUID(userId)),
            publishedAt = publishedAt.let(Instant::parse),
        )

    override fun save(post: Post, expiresFromCacheAt: Instant) =
        database.postsQueries.save(
            Posts(
                id = post.id.toString(),
                userId = post.userId.toString(),
                body = post.body,
                publishedAt = post.publishedAt.toString(),
                expiresFromCacheAt = expiresFromCacheAt.toString()
            )
        )

    override fun get(id: UUID): Post? =
        database.postsQueries.get(id.toString(), Clock.System.now().toString())
            .executeAsOneOrNull()?.toPost()

    override fun delete(id: UUID) =
        database.postsQueries.delete(id.toString())

    override fun deleteAll() =
        database.postsQueries.deleteAll()

    override fun deleteExpired() =
        database.postsQueries.deleteExpired(Clock.System.now().toString())

}
