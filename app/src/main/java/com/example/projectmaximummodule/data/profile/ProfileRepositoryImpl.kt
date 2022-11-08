package com.example.projectmaximummodule.data.profile

import com.example.projectmaximummodule.data.network.retorfit.response.ProfileResponse
import com.example.projectmaximummodule.data.network.retorfit.response.ShortPositionResponse
import com.example.projectmaximummodule.data.profile.remote.ProfileRemoteDataSource
import com.example.projectmaximummodule.util.RemoteResult
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProfileRemoteDataSource
): ProfileRepository {

    override suspend fun getProfileInfo(): RemoteResult<ProfileResponse, Throwable> {
        return remoteDataSource.getProfileInfo()
    }

    override suspend fun getShortList(groupId: Long): RemoteResult<List<ShortPositionResponse>, Throwable> {
        return remoteDataSource.getShortList(groupId)
    }

    override suspend fun getFullRating(groupId: Long) {

    }
}