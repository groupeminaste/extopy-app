package com.extopy.usecases.auth

import com.extopy.models.auth.AuthToken
import dev.kaccelero.usecases.ISuspendUseCase

interface IFetchTokenUseCase : ISuspendUseCase<String, AuthToken?>
