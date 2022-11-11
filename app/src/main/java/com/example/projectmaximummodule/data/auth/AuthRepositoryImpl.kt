package com.example.projectmaximummodule.data.auth

import com.example.projectmaximummodule.data.auth.remote.AuthRemoteDataSource
import com.example.projectmaximummodule.data.auth.remote.request.LoginRequest
import com.example.projectmaximummodule.data.auth.remote.resposne.UserResponse
import com.example.projectmaximummodule.util.RemoteResult
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
): AuthRepository {

    override val currentUser: UserResponse?
        get() = TODO("Not yet implemented")

    override suspend fun login(request: LoginRequest): RemoteResult<Unit, Throwable> {
        return authRemoteDataSource.login(request)
    }

    override suspend fun logout() {
        authRemoteDataSource.logout()
    }

    override suspend fun checkLogin(): RemoteResult<Unit, Throwable> {
        return authRemoteDataSource.refreshOauthIfNeeded()
    }
}