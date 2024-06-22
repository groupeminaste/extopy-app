package com.extopy.usecases.users

import com.extopy.models.users.User
import dev.kaccelero.usecases.ISuspendUseCase

interface IUpdateFollowInUserUseCase : ISuspendUseCase<User, User?>
