package com.example.projectmaximummodule.data.auth.remote

import com.example.projectmaximummodule.core.application.AppSharedPreferences
import com.example.projectmaximummodule.data.network.interceptors.GetCookieTokenInterceptor
import com.example.projectmaximummodule.data.network.retorfit.MainApiService
import com.example.projectmaximummodule.data.auth.remote.request.LoginRequest
import com.example.projectmaximummodule.data.auth.remote.request.OauthRequest
import com.example.projectmaximummodule.di.Oauth
import com.example.projectmaximummodule.util.RemoteResult
import com.example.projectmaximummodule.util.runResultCatching
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

    override suspend fun login(request: LoginRequest) = runResultCatching {
        loginApi.login(request)
        val userResponse = loginApi.checkAuth()
        sharedPreferences.setRefreshToken(GetCookieTokenInterceptor.token)
        if (userResponse != null) {
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
            sharedPreferences.setSession(GetCookieTokenInterceptor.session)
        }
    }

    override suspend fun logout() {

    }

    override suspend fun refreshOauthIfNeeded(): RemoteResult<Unit, Throwable> {
        val result = runResultCatching {
            oauthApi.getInfoAboutMe()
        }
        return when(result) {
            is RemoteResult.Success -> RemoteResult.Success(Unit)
            is RemoteResult.Failed -> refreshOauth()
        }
    }

    override suspend fun refreshOauth() = runResultCatching {
        sharedPreferences.clearAccessToken()
        GetCookieTokenInterceptor.session = sharedPreferences.getSession()?: ""
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