package com.extopy.usecases.auth

import com.extopy.models.users.User
import dev.kaccelero.usecases.IUnitSuspendUseCase

interface IGetCurrentUserUseCase : IUnitSuspendUseCase<User?>
