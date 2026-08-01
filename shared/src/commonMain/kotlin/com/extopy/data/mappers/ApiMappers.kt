package com.extopy.data.mappers

import com.extopy.api.responses.posts.PostResponse
import com.extopy.api.responses.timelines.TimelineResponse
import com.extopy.api.responses.users.UserResponse
import com.extopy.domain.models.posts.Post
import com.extopy.domain.models.timelines.Timeline
import com.extopy.domain.models.users.User

/**
 * Translates what the API returns into what the app works with.
 *
 * This is the only place that knows both vocabularies. The domain models drop the fields the app
 * has no use for (`email`, `banned`, `visibility`…) rather than carrying them around unread, so
 * adding a field to the API costs nothing here until something actually needs it.
 */

fun UserResponse.toDomain() = User(
    id = id,
    displayName = displayName,
    username = username,
    biography = biography,
    avatar = avatar,
    joinDate = joinDate,
    verified = verified,
    postsCount = postsCount,
    followersCount = followersCount,
    followingCount = followingCount,
    followersIn = followersIn,
    followingIn = followingIn,
)

fun PostResponse.toDomain() = Post(
    id = id,
    userId = userId,
    user = user?.toDomain(),
    repliedToId = repliedToId,
    repostOfId = repostOfId,
    body = body,
    publishedAt = publishedAt,
    editedAt = editedAt,
    likesCount = likesCount,
    repliesCount = repliesCount,
    repostsCount = repostsCount,
    likesIn = likesIn,
)

fun TimelineResponse.toDomain() = Timeline(
    id = id,
)
