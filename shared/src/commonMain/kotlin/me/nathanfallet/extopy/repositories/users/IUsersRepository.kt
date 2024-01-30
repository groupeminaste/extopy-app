package me.nathanfallet.extopy.repositories.users

import kotlinx.datetime.Instant
import me.nathanfallet.extopy.models.users.User

interface IUsersRepository {

    fun save(user: User, expiresFromCacheAt: Instant)
    fun get(id: String): User?
    fun delete(id: String)
    fun deleteAll()
    fun deleteExpired()

}
