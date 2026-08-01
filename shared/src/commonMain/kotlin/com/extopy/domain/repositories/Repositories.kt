package com.extopy.domain.repositories

import com.extopy.domain.models.posts.Post
import com.extopy.domain.models.timelines.Timeline
import com.extopy.domain.models.users.User
import kotlin.uuid.Uuid

/**
 * Reads and writes posts.
 *
 * Listings are windowed by [limit] and [offset]; leaving them out lets the server apply its own
 * defaults. Whether a read was served from the local cache or from the network is an implementation
 * detail callers never see.
 */
interface PostsRepository {

    suspend fun list(limit: Long? = null, offset: Long? = null, search: String? = null): List<Post>

    /** The post, or null if there is no such post. */
    suspend fun get(id: Uuid): Post?

    suspend fun create(body: String, repliedToId: Uuid? = null, repostOfId: Uuid? = null): Post

    suspend fun listReplies(id: Uuid, limit: Long? = null, offset: Long? = null): List<Post>

    /** Likes the post on behalf of the signed-in user. */
    suspend fun like(id: Uuid)

    /** Removes [userId]'s like from the post. */
    suspend fun unlike(id: Uuid, userId: Uuid)

}

/** Reads users and follows them. */
interface UsersRepository {

    suspend fun list(limit: Long? = null, offset: Long? = null, search: String? = null): List<User>

    /** The user, or null if there is no such user. */
    suspend fun get(id: Uuid): User?

    suspend fun listPosts(id: Uuid, limit: Long? = null, offset: Long? = null): List<Post>

    /** Follows the user on behalf of the signed-in user. */
    suspend fun follow(id: Uuid)

    /** Removes [followerId] from the user's followers. */
    suspend fun unfollow(id: Uuid, followerId: Uuid)

}

/** Reads timelines and the posts they hold. */
interface TimelinesRepository {

    /** The timeline, or null if there is no such timeline. */
    suspend fun get(id: Uuid): Timeline?

    suspend fun listPosts(id: Uuid, limit: Long? = null, offset: Long? = null): List<Post>

}

/**
 * Who is signed in.
 *
 * Only the user id lives here: the tokens are held by the HTTP client, which is what refreshes
 * them, and nothing outside the data layer ever needs to read one.
 */
interface SessionRepository {

    fun getUserId(): Uuid?

    fun setUserId(userId: Uuid?)

}

/**
 * The local copy of what was read from the API.
 *
 * Cached rows carry viewer-relative state (whether *you* liked a post, follow a user), so the
 * cache belongs to the session that filled it and has to be dropped when that session ends.
 */
interface CacheRepository {

    suspend fun clear()

}

/** Signs in and out. */
interface AuthRepository {

    /**
     * The web page to send the user to so they can authorize this client. It redirects back with
     * the code [authenticate] expects.
     */
    val authorizationUrl: String

    /**
     * Exchanges an OAuth authorization code for a session, and returns the id of the user it
     * belongs to.
     */
    suspend fun authenticate(code: String): Uuid

    /** Drops the session, locally. */
    suspend fun logout()

}
