package me.nathanfallet.extopy.repositories.users

import me.nathanfallet.extopy.models.users.User

interface IUsersRepository {

    fun upsert(user: User)
    fun get(id: String): User?

}
