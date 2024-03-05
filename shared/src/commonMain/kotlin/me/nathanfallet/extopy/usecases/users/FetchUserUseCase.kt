package me.nathanfallet.extopy.usecases.users

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import me.nathanfallet.extopy.client.IExtopyClient
import me.nathanfallet.extopy.models.users.User
import me.nathanfallet.extopy.repositories.users.IUsersRepository

class FetchUserUseCase(
    private val client: IExtopyClient,
    private val usersRepository: IUsersRepository,
) : IFetchUserUseCase {

    override suspend fun invoke(input: String): User? =
        usersRepository.get(input) ?: client.users.get(input)?.also {
            usersRepository.save(
                it,
                Clock.System.now().plus(60, DateTimeUnit.SECOND, TimeZone.currentSystemDefault())
            )
        }

}
