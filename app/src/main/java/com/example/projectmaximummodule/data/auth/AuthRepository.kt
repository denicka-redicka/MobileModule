package com.example.projectmaximummodule.data.auth

import com.example.projectmaximummodule.data.auth.remote.request.LoginRequest
import com.example.projectmaximummodule.data.auth.remote.resposne.UserResponse

interface AuthRepository {

    val currentUser: UserResponse?

    suspend fun login(user: LoginRequest)

    suspend fun logout()
}