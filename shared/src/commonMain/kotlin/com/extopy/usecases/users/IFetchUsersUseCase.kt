package com.extopy.usecases.users

import com.extopy.models.users.User
import dev.kaccelero.repositories.Pagination
import dev.kaccelero.usecases.ISuspendUseCase

interface IFetchUsersUseCase : ISuspendUseCase<Pagination, List<User>>
