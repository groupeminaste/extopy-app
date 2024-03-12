package me.nathanfallet.extopy.usecases.users

import me.nathanfallet.extopy.client.IExtopyClient
import me.nathanfallet.extopy.models.users.User
import me.nathanfallet.usecases.pagination.Pagination

class FetchUsersUseCase(
    private val client: IExtopyClient,
) : IFetchUsersUseCase {

    override suspend fun invoke(input: Pagination): List<User> = client.users.list(input)

}
