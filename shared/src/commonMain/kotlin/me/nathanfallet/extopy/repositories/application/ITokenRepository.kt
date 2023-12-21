package me.nathanfallet.extopy.repositories.application

interface ITokenRepository {

    fun getToken(): String?
    fun setToken(token: String?)

    fun getUserId(): String?
    fun setUserId(userId: String?)

}
