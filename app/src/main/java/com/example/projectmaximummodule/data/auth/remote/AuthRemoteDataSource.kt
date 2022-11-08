package com.example.projectmaximummodule.data.auth.remote

import com.example.projectmaximummodule.data.auth.remote.request.LoginRequest
import com.example.projectmaximummodule.data.auth.remote.resposne.UserResponse

interface AuthRemoteDataSource {

    val currentUser: UserResponse?

    suspend fun login(user: LoginRequest)

    suspend fun logout()
}