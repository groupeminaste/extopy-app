package com.extopy.usecases.auth

import com.extopy.models.auth.AuthToken
import dev.kaccelero.usecases.IUseCase

interface ISetTokenUseCase : IUseCase<AuthToken?, Unit>
