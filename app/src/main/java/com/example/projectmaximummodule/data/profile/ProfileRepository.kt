package com.example.projectmaximummodule.data.profile

import com.example.projectmaximummodule.data.network.retorfit.response.ProfileResponse
import com.example.projectmaximummodule.data.network.retorfit.response.ShortPositionResponse
import com.example.projectmaximummodule.util.RemoteResult

interface ProfileRepository {

    suspend fun getProfileInfo(): RemoteResult<ProfileResponse, Throwable>

    suspend fun getShortList(groupId: Long): RemoteResult<List<ShortPositionResponse>, Throwable>

    suspend fun getFullRating(groupId: Long)
}