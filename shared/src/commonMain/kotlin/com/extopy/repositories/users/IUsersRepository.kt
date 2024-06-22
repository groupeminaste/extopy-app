package com.extopy.repositories.users

import com.extopy.models.users.User
import dev.kaccelero.models.UUID
import kotlinx.datetime.Instant

interface IUsersRepository {

    fun save(user: User, expiresFromCacheAt: Instant)
    fun get(id: UUID): User?
    fun delete(id: UUID)
    fun deleteAll()
    fun deleteExpired()

}
