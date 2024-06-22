package com.extopy.usecases.users

import com.extopy.client.IExtopyClient
import com.extopy.models.users.User
import com.extopy.repositories.users.IUsersRepository
import dev.kaccelero.models.UUID
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus

class FetchUserUseCase(
    private val client: IExtopyClient,
    private val usersRepository: IUsersRepository,
) : IFetchUserUseCase {

    override suspend fun invoke(input: UUID): User? =
        usersRepository.get(input) ?: client.users.get(input)?.also {
            usersRepository.save(
                it,
                Clock.System.now().plus(60, DateTimeUnit.SECOND, TimeZone.currentSystemDefault())
            )
        }

}
