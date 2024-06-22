package com.extopy.usecases.users

import com.extopy.client.IExtopyClient
import com.extopy.models.users.User
import dev.kaccelero.repositories.Pagination

class FetchUsersUseCase(
    private val client: IExtopyClient,
) : IFetchUsersUseCase {

    override suspend fun invoke(input: Pagination): List<User> = client.users.list(input)

}
