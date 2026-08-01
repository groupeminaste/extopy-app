package com.extopy.data.mappers

import com.extopy.data.database.entities.PostEntity
import com.extopy.data.database.entities.UserEntity
import com.extopy.domain.models.posts.Post
import com.extopy.domain.models.users.User
import kotlin.time.Instant
import kotlin.uuid.Uuid

/**
 * Translates between the domain models and the rows the cache stores.
 *
 * A cached post never brings its author back with it: the author is a row of its own, and the
 * repository reads it separately so one user is stored once rather than once per post.
 */

fun UserEntity.toDomain() = User(
    id = Uuid.parse(id),
    displayName = displayName,
    username = username,
    biography = biography,
    avatar = avatar,
    joinDate = joinDate?.let(Instant::fromEpochMilliseconds),
    verified = verified,
    postsCount = postsCount,
    followersCount = followersCount,
    followingCount = followingCount,
    followersIn = followersIn,
    followingIn = followingIn,
)

fun User.toEntity(expiresAt: Instant) = UserEntity(
    id = id.toString(),
    displayName = displayName,
    username = username,
    biography = biography,
    avatar = avatar,
    joinDate = joinDate?.toEpochMilliseconds(),
    verified = verified,
    postsCount = postsCount,
    followersCount = followersCount,
    followingCount = followingCount,
    followersIn = followersIn,
    followingIn = followingIn,
    expiresAt = expiresAt.toEpochMilliseconds(),
)

fun PostEntity.toDomain(user: User?) = Post(
    id = Uuid.parse(id),
    userId = Uuid.parse(userId),
    user = user,
    repliedToId = repliedToId?.let(Uuid::parse),
    repostOfId = repostOfId?.let(Uuid::parse),
    body = body,
    publishedAt = Instant.fromEpochMilliseconds(publishedAt),
    editedAt = editedAt?.let(Instant::fromEpochMilliseconds),
    likesCount = likesCount,
    repliesCount = repliesCount,
    repostsCount = repostsCount,
    likesIn = likesIn,
)

fun Post.toEntity(expiresAt: Instant) = PostEntity(
    id = id.toString(),
    userId = userId.toString(),
    repliedToId = repliedToId?.toString(),
    repostOfId = repostOfId?.toString(),
    body = body,
    publishedAt = publishedAt.toEpochMilliseconds(),
    editedAt = editedAt?.toEpochMilliseconds(),
    likesCount = likesCount,
    repliesCount = repliesCount,
    repostsCount = repostsCount,
    likesIn = likesIn,
    expiresAt = expiresAt.toEpochMilliseconds(),
)
