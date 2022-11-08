package com.example.projectmaximummodule.data.auth

import com.example.projectmaximummodule.data.auth.remote.AuthRemoteDataSource
import com.example.projectmaximummodule.data.auth.remote.request.LoginRequest
import com.example.projectmaximummodule.data.auth.remote.resposne.UserResponse
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
): AuthRepository {

    override val currentUser: UserResponse?
        get() = TODO("Not yet implemented")

    override suspend fun login(user: LoginRequest) {
        authRemoteDataSource.login(user)
    }

    override suspend fun logout() {
        authRemoteDataSource.logout()
    }
}