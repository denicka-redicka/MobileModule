package com.example.projectmaximummodule.data.network.retorfit

import com.example.projectmaximummodule.data.network.retorfit.request.OauthRequest
import com.example.projectmaximummodule.data.network.retorfit.response.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MainApiService {

    //Oauth
    @GET("auth/redirect-to-oauth-provider?from_url=https://education.maximumtest.ru/")
    suspend fun redirectToOauth()

    @POST("auth/oauth-result")
    suspend fun oauthResult(@Body oauthRequest: OauthRequest)

    @GET("profile/student/groups")
    suspend fun getGroupsList(): List<GroupResponse>

    @GET("schedule/student/schedule/groups/{id}/lessons")
    suspend fun getLessons(@Path("id") groupId: Long): LessonsResponse


    @GET("schedule/student/schedule/groups/{id}/debts")
    suspend fun getDebts(@Path("id") groupId: Long): DebtsResponse

    @GET("profile/external/group/{id}/rating/leaderboard/short")
    suspend fun getShortList(@Path("id") groupId: Long): List<ShortPositionResponse>

    @GET("profile/external/auth/me")
    suspend fun getProfile(): ProfileResponse

    @GET("schedule/student/schedule/groups/{id}/todolist")
    suspend fun getToDoList(@Path("id") groupId: Long): List<ToDoResponse>

    @GET("schedule/student/schedule/groups/{id}/curriculums?withKbs=1")
    suspend fun getAllTheory(@Path("id") id: Long): @kotlinx.serialization.Serializable Map<Long, List<TheoryResponse>>

    @GET("content/knowledgebase/{id}")
    suspend fun getKnowledgeBase(@Path("id") id:Int): TheoryItem

    @GET("schedule/student/schedule/groups/{id}/lessons?&withCurriculums=0&all=1")
    suspend fun getLessonsListWithType(
        @Path("id") id: Long,
        @Query("lessonType") lessonType: String
    ): LessonsResponse

    @GET("profile/external/group/{id}/statistics")
    suspend fun getStatistics(@Path("id") groupId: Long): GroupStatisticsResponse
}