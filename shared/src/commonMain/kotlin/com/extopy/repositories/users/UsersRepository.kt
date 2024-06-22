package com.extopy.repositories.users

import com.extopy.database.Database
import com.extopy.database.Users
import com.extopy.models.users.User
import dev.kaccelero.models.UUID
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class UsersRepository(
    private val database: Database,
) : IUsersRepository {

    private fun Users.toUser() =
        User(
            id = UUID(id),
            displayName = displayName,
            username = username,
            email = email,
            biography = biography,
            avatar = avatar
        )

    override fun save(user: User, expiresFromCacheAt: Instant) =
        database.usersQueries.save(
            Users(
                id = user.id.toString(),
                displayName = user.displayName,
                username = user.username,
                email = user.email,
                biography = user.biography,
                avatar = user.avatar,
                expiresFromCacheAt = expiresFromCacheAt.toString()
            )
        )

    override fun get(id: UUID): User? =
        database.usersQueries.get(id.toString(), Clock.System.now().toString())
            .executeAsOneOrNull()?.toUser()

    override fun delete(id: UUID) =
        database.usersQueries.delete(id.toString())

    override fun deleteAll() =
        database.usersQueries.deleteAll()

    override fun deleteExpired() =
        database.usersQueries.deleteExpired(Clock.System.now().toString())

}
