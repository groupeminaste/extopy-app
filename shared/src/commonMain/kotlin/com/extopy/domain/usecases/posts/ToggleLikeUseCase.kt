package com.extopy.domain.usecases.posts

import com.extopy.domain.models.posts.Post
import com.extopy.domain.repositories.PostsRepository
import com.extopy.domain.repositories.SessionRepository

/**
 * Likes a post, or takes the like back.
 *
 * The returned post carries the new state so the list can be updated without a round trip: the
 * server answers the write but does not hand back the post, and re-reading it just to move a
 * counter by one would make the button feel slow.
 */
class ToggleLikeUseCase(
    private val posts: PostsRepository,
    private val session: SessionRepository,
) {

    suspend operator fun invoke(post: Post): Post? {
        val userId = session.getUserId() ?: return null
        val liked = post.likesIn == true
        if (liked) posts.unlike(post.id, userId) else posts.like(post.id)
        return post.copy(
            likesIn = !liked,
            likesCount = (post.likesCount ?: 0) + if (liked) -1 else 1,
        )
    }

}
