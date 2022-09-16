package com.example.projectmaximummodule.data.network.retorfit

import com.example.projectmaximummodule.data.network.retorfit.request.OauthRequest
import com.example.projectmaximummodule.data.network.retorfit.response.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MainApiService {

    //Oauth
    @GET("auth/redirect-to-oauth-provider?from_url=https://education.maximumtest.ru/")
    suspend fun redirectToOauth()

    @POST("auth/oauth-result")
    suspend fun oauthResult(@Body oauthRequest: OauthRequest)

    @GET("profile/student/groups")
    suspend fun getGroupsList(): List<GroupResponse>

    @GET("schedule/student/schedule/groups/{id}/lessons")
    suspend fun getLessons(@Path("id") groupId: Long): ScheduleResponse

    @GET("profile/external/group/{id}/rating/leaderboard/short")
    suspend fun getShortList(@Path("id") groupId: Long): List<ShortPositionResponse>

    @GET("profile/external/auth/me")
    suspend fun getProfile(): ProfileResponse

    @GET("schedule/student/schedule/groups/{id}/todolist")
    suspend fun getToDoList(@Path("id") groupId: Long): List<ToDoResponse>

    @GET("auth/logout-link")
    suspend fun logout()

    @GET("profile/external/group/{id}/statistics")
    suspend fun getStatistics(@Path("id") groupId: Long): GroupStatisticsResponse
}