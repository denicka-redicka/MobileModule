package com.example.projectmaximummodule.data.auth.remote

import com.example.projectmaximummodule.data.auth.remote.request.LoginRequest
import com.example.projectmaximummodule.data.auth.remote.resposne.OauthResponse
import com.example.projectmaximummodule.data.auth.remote.resposne.UserResponse
import retrofit2.http.*

interface LoginApiService {

    @POST("profile/auth/email/login/")
    suspend fun login(@Body loginRequest: LoginRequest)

    @GET("system/check-auth")
    suspend fun checkAuth(): UserResponse?

    @GET("oauth/authorize/?client_id=1&redirect_uri=https://education.maximumtest.ru/auth/oauth-result/&response_type=code&scope=%2A&from_url=https://education.maximumtest.ru/")
    suspend fun authorize(@Query("state") state: String): OauthResponse
}