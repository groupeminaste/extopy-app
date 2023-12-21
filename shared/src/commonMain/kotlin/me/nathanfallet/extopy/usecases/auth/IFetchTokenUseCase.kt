package me.nathanfallet.extopy.usecases.auth

import me.nathanfallet.usecases.auth.AuthToken
import me.nathanfallet.usecases.base.ISuspendUseCase

interface IFetchTokenUseCase : ISuspendUseCase<String, AuthToken?>
