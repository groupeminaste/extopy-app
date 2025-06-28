package com.extopy.repositories.application

import dev.kaccelero.models.UUID
import dev.kaccelero.repositories.INativeSettingsRepository

class ApplicationRepository(
    private val nativeSettingsRepository: INativeSettingsRepository,
) : IApplicationRepository {

    override fun getToken(): String? = nativeSettingsRepository.getSecureString("token")
    override fun setToken(token: String?) = token?.let { nativeSettingsRepository.setSecureString("token", it) }
        ?: nativeSettingsRepository.removeSecure("token")

    override fun getRefreshToken(): String? = nativeSettingsRepository.getSecureString("refreshToken")
    override fun setRefreshToken(token: String?) =
        token?.let { nativeSettingsRepository.setSecureString("refreshToken", it) }
            ?: nativeSettingsRepository.removeSecure("refreshToken")

    override fun getUserId(): UUID? = nativeSettingsRepository.getSecureUUID("userId")
    override fun setUserId(userId: UUID?) = userId?.let { nativeSettingsRepository.setSecureUUID("userId", it) }
        ?: nativeSettingsRepository.removeSecure("userId")

}
