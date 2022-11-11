package com.example.projectmaximummodule.data.auth.remote

import com.example.projectmaximummodule.data.auth.remote.request.LoginRequest
import com.example.projectmaximummodule.util.RemoteResult

interface AuthRemoteDataSource {

    suspend fun login(request: LoginRequest): RemoteResult<Unit, Throwable>

    suspend fun logout()

    suspend fun refreshOauthIfNeeded():  RemoteResult<Unit, Throwable>

    suspend fun refreshOauth(): RemoteResult<Unit, Throwable>
}