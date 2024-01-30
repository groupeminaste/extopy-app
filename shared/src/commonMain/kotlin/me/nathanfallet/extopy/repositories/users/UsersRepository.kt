package me.nathanfallet.extopy.repositories.users

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import me.nathanfallet.extopy.database.Database
import me.nathanfallet.extopy.database.Users
import me.nathanfallet.extopy.models.users.User

class UsersRepository(
    private val database: Database,
) : IUsersRepository {

    override fun save(user: User, expiresFromCacheAt: Instant) =
        database.usersQueries.save(
            Users(
                id = user.id,
                displayName = user.displayName,
                username = user.username,
                email = user.email,
                biography = user.biography,
                avatar = user.avatar,
                expiresFromCacheAt = expiresFromCacheAt.toString()
            )
        )

    override fun get(id: String): User? =
        database.usersQueries.get(id, Clock.System.now().toString()).executeAsOneOrNull()?.let {
            User(
                id = it.id,
                displayName = it.displayName,
                username = it.username,
                email = it.email,
                biography = it.biography,
                avatar = it.avatar
            )
        }

    override fun delete(id: String) =
        database.usersQueries.delete(id)

    override fun deleteAll() =
        database.usersQueries.deleteAll()

    override fun deleteExpired() =
        database.usersQueries.deleteExpired(Clock.System.now().toString())

}
