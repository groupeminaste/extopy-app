package com.extopy.domain.usecases.auth

import com.extopy.domain.models.users.User
import com.extopy.domain.repositories.AuthRepository
import com.extopy.domain.repositories.SessionRepository
import com.extopy.domain.repositories.UsersRepository

/**
 * Turns an OAuth authorization code into a signed-in session.
 *
 * The exchange, remembering who signed in, and reading that user back are one step for callers:
 * a half-done sign-in — a session with no user on screen — is not a state the app has a place for.
 */
class AuthenticateUseCase(
    private val auth: AuthRepository,
    private val session: SessionRepository,
    private val users: UsersRepository,
) {

    suspend operator fun invoke(code: String): User? {
        val userId = auth.authenticate(code)
        session.setUserId(userId)
        return users.get(userId)
    }

}
