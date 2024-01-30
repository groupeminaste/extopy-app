package me.nathanfallet.extopy.repositories.users

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import me.nathanfallet.extopy.database.Database
import me.nathanfallet.extopy.database.Users
import me.nathanfallet.extopy.models.users.User

class UsersRepository(
    private val database: Database,
) : IUsersRepository {

    override fun upsert(user: User) = database.usersQueries.upsert(
        id = user.id,
        displayName = user.displayName,
        username = user.username,
        email = user.email,
        biography = user.biography,
        avatar = user.avatar,
        expiresFromCacheAt = Clock.System.now()
            .plus(60, DateTimeUnit.SECOND, TimeZone.currentSystemDefault())
            .toString()
    )

    override fun get(id: String): User? = database.usersQueries.get(id)
        .executeAsOneOrNull()?.toUser()

    private fun Users.toUser() = User(
        id = id,
        displayName = displayName,
        username = username,
        email = email,
        biography = biography,
        avatar = avatar
    )

}
