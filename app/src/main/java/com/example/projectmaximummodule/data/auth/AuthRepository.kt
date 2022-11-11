package com.example.projectmaximummodule.data.auth

import com.example.projectmaximummodule.data.auth.remote.request.LoginRequest
import com.example.projectmaximummodule.data.auth.remote.resposne.UserResponse
import com.example.projectmaximummodule.util.RemoteResult

interface AuthRepository {

    val currentUser: UserResponse?

    suspend fun login(request: LoginRequest): RemoteResult<Unit, Throwable>

    suspend fun logout()

    suspend fun checkLogin(): RemoteResult<Unit, Throwable>
}