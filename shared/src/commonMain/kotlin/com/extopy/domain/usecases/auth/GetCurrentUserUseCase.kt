package com.extopy.domain.usecases.auth

import com.extopy.domain.models.users.User
import com.extopy.domain.repositories.SessionRepository
import com.extopy.domain.repositories.UsersRepository

/**
 * Reads the signed-in user, or null when nobody is signed in.
 */
class GetCurrentUserUseCase(
    private val session: SessionRepository,
    private val users: UsersRepository,
) {

    suspend operator fun invoke(): User? = session.getUserId()?.let { users.get(it) }

}
