package com.extopy.domain.usecases.users

import com.extopy.domain.models.users.User
import com.extopy.domain.repositories.SessionRepository
import com.extopy.domain.repositories.UsersRepository

/**
 * Follows a user, or unfollows them.
 *
 * As with liking a post, the returned user carries the new state so the button can settle without
 * waiting on a second read.
 */
class ToggleFollowUseCase(
    private val users: UsersRepository,
    private val session: SessionRepository,
) {

    suspend operator fun invoke(user: User): User? {
        val userId = session.getUserId() ?: return null
        val following = user.followersIn == true
        if (following) users.unfollow(user.id, userId) else users.follow(user.id)
        return user.copy(
            followersIn = !following,
            followersCount = (user.followersCount ?: 0) + if (following) -1 else 1,
        )
    }

}
