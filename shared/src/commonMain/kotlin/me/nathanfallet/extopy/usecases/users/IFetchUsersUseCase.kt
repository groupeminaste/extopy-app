package me.nathanfallet.extopy.usecases.users

import me.nathanfallet.extopy.models.users.User
import me.nathanfallet.usecases.base.ISuspendUseCase
import me.nathanfallet.usecases.pagination.Pagination

interface IFetchUsersUseCase : ISuspendUseCase<Pagination, List<User>>
