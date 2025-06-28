package com.extopy.repositories.application

import dev.kaccelero.models.UUID

interface IApplicationRepository {

    fun getToken(): String?
    fun setToken(token: String?)
    fun getRefreshToken(): String?
    fun setRefreshToken(token: String?)

    fun getUserId(): UUID?
    fun setUserId(userId: UUID?)

}
