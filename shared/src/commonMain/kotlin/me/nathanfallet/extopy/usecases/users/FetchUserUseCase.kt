package me.nathanfallet.extopy.usecases.users

import me.nathanfallet.extopy.client.IExtopyClient
import me.nathanfallet.extopy.models.users.User
import me.nathanfallet.extopy.repositories.users.IUsersRepository

class FetchUserUseCase(
    private val client: IExtopyClient,
    private val usersRepository: IUsersRepository,
) : IFetchUserUseCase {

    override suspend fun invoke(input: String): User? {
        return usersRepository.get(input) ?: client.users.get(input)?.also {
            usersRepository.upsert(it)
        }
    }

}
