package me.nathanfallet.extopy.usecases.users

import me.nathanfallet.extopy.client.IExtopyClient
import me.nathanfallet.extopy.models.users.User

class FetchUserUseCase(
    private val client: IExtopyClient,
) : IFetchUserUseCase {

    override suspend fun invoke(input: String): User? {
        // TODO: Read from cache (local db)
        return client.users.get(input)?.also {
            // TODO: Save in cache (local db)
        }
    }

}
