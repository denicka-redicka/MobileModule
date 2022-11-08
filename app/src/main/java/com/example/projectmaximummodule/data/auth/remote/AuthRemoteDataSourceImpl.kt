package com.example.projectmaximummodule.data.auth.remote

import com.example.projectmaximummodule.application.AppSharedPreferences
import com.example.projectmaximummodule.data.network.interceptors.GetCookieTokenInterceptor
import com.example.projectmaximummodule.data.network.retorfit.MainApiService
import com.example.projectmaximummodule.data.auth.remote.request.LoginRequest
import com.example.projectmaximummodule.data.auth.remote.request.OauthRequest
import com.example.projectmaximummodule.data.auth.remote.resposne.UserResponse
import com.example.projectmaximummodule.di.Oauth
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val loginApi: LoginApiService,
    @Oauth val oauthApi: MainApiService,
    private val sharedPreferences: AppSharedPreferences
): AuthRemoteDataSource {

    private companion object {
        private const val EDUCATION_URL = "https://education.maximumtest.ru/"
        private const val CODE_POSITION = 0
        private const val STATE_POSITION = 1
        private const val SESSION_POSITION = 2
    }

    override val currentUser: UserResponse?
        get() = TODO("Not yet implemented")

    override suspend fun login(user: LoginRequest) {
        loginApi.login(user)
        val user = loginApi.checkAuth()
        if (user != null) {
            oauthApi.redirectToOauth()
            val oauthResponse = loginApi.authorize(GetCookieTokenInterceptor.state)
            val response = oauthResponse.separateResponse()
            val oauthRequest = OauthRequest(
                code = response[CODE_POSITION].substring(response[CODE_POSITION].indexOf("=")+1),
                session = response[SESSION_POSITION].substring(response[SESSION_POSITION].indexOf("=")+1),
                state = response[STATE_POSITION].substring(response[STATE_POSITION].indexOf("=")+1),
                fromUrl = EDUCATION_URL
            )
            oauthApi.oauthResult(oauthRequest)
            sharedPreferences.setAccessToken(GetCookieTokenInterceptor.token)
        }
    }

    override suspend fun logout() {
        TODO("Do not forget to implement")
    }
}