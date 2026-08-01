package com.extopy.domain.usecases.auth

import com.extopy.domain.repositories.AuthRepository
import com.extopy.domain.repositories.CacheRepository
import com.extopy.domain.repositories.SessionRepository

/**
 * Signs the user out: drops the tokens, forgets who they belonged to, and empties the cache.
 *
 * All three matter. A user id left behind would have the app try to read a profile it can no longer
 * authenticate for, and cached rows carry viewer-relative state — whoever signs in next would see
 * the previous user's likes and follows.
 */
class LogoutUseCase(
    private val auth: AuthRepository,
    private val session: SessionRepository,
    private val cache: CacheRepository,
) {

    suspend operator fun invoke() {
        auth.logout()
        session.setUserId(null)
        cache.clear()
    }

}
