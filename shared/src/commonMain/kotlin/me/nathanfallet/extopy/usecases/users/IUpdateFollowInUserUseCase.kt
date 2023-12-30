package me.nathanfallet.extopy.usecases.users

import me.nathanfallet.extopy.models.users.User
import me.nathanfallet.usecases.base.ISuspendUseCase

interface IUpdateFollowInUserUseCase : ISuspendUseCase<User, User?>
