package com.example.projectmaximummodule.data.profile.remote

import com.example.projectmaximummodule.data.network.retorfit.MainApiService
import com.example.projectmaximummodule.util.runResultCatching
import javax.inject.Inject

class ProfileRemoteDataSourceImpl @Inject constructor(
    private val api: MainApiService
): ProfileRemoteDataSource {

    override suspend fun getProfileInfo() = runResultCatching {
        api.getProfile()
    }

    override suspend fun getShortList(groupId: Long) = runResultCatching{
        api.getShortList(groupId)
    }

    override suspend fun getFullRating(groupId: Long) {

    }
}