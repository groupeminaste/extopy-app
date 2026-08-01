package com.extopy.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * The rows the local cache holds.
 *
 * This is a cache, not a copy of the server: ids are stored as their text form and instants as
 * epoch milliseconds, and [expiresAt] is when the row stops being worth reading — past it, the row
 * is ignored and read again from the network.
 *
 * The viewer-relative fields (`likesIn`, `followersIn`…) are cached too, which is only sound
 * because signing out drops the whole cache — see [com.extopy.domain.repositories.CacheRepository].
 */

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val displayName: String,
    val username: String,
    val biography: String?,
    val avatar: String?,
    val joinDate: Long?,
    val verified: Boolean?,
    val postsCount: Long?,
    val followersCount: Long?,
    val followingCount: Long?,
    val followersIn: Boolean?,
    val followingIn: Boolean?,
    val expiresAt: Long,
)

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val repliedToId: String?,
    val repostOfId: String?,
    val body: String?,
    val publishedAt: Long,
    val editedAt: Long?,
    val likesCount: Long?,
    val repliesCount: Long?,
    val repostsCount: Long?,
    val likesIn: Boolean?,
    val expiresAt: Long,
)
