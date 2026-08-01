package com.extopy.data.repositories

import com.extopy.data.database.daos.PostDao
import com.extopy.data.database.daos.UserDao
import com.extopy.data.mappers.toEntity
import com.extopy.domain.models.posts.Post
import com.extopy.domain.models.users.User
import kotlin.time.Clock
import kotlin.time.Duration.Companion.seconds

/**
 * How long a cached row stays worth reading.
 *
 * Short on purpose: the cache exists so that opening a post you just scrolled past draws instantly,
 * not so that the app works offline. A minute is long enough to cover that, and short enough that a
 * like someone else just added shows up on the next look.
 */
internal val CACHE_LIFETIME = 60.seconds

internal suspend fun UserDao.cache(user: User) =
    save(user.toEntity(Clock.System.now() + CACHE_LIFETIME))

/**
 * Stores a post, and the author it came with.
 *
 * Every listing endpoint returns posts with their author inlined, so caching both here is what lets
 * a cached post be read back whole.
 */
internal suspend fun PostDao.cache(post: Post, users: UserDao) {
    save(post.toEntity(Clock.System.now() + CACHE_LIFETIME))
    post.user?.let { users.cache(it) }
}
