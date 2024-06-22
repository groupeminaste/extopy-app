package com.extopy.usecases.users

import com.extopy.models.users.User
import dev.kaccelero.models.UUID
import dev.kaccelero.usecases.ISuspendUseCase

interface IFetchUserUseCase : ISuspendUseCase<UUID, User?>
