package com.example.projectmaximummodule.data.network.retorfit

import com.example.projectmaximummodule.data.network.retorfit.request.OauthRequest
import com.example.projectmaximummodule.data.network.retorfit.request.TestAnswerRequest
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

    //GroupList
    @GET("profile/student/groups")
    suspend fun getGroupsList(): List<GroupResponse>

    //Schedule
    @GET("schedule/student/schedule/groups/{id}/lessons")
    suspend fun getLessons(@Path("id") groupId: Long): LessonsResponse

    @GET("schedule/student/schedule/groups/{id}/todolist")
    suspend fun getToDoList(@Path("id") groupId: Long): List<ToDoResponse>

    @GET("profile/external/group/{id}/statistics")
    suspend fun getStatistics(@Path("id") groupId: Long): GroupStatisticsResponse

    //Homework
    @GET("schedule/student/schedule/groups/{id}/debts")
    suspend fun getDebts(@Path("id") groupId: Long): DebtsResponse

    @GET("content/practice/curriculumsubject/{id}")
    suspend fun getHomeworkCurriculumSubject(@Path("id") id: Long): HomeworkCurriculumSubjectResponse

    @GET("content/practice/curriculumsubject/{group_id}/knowledgebase/{subject_id}/{type}/tests")
    suspend fun getTests(
        @Path("group_id") groupId: Long,
        @Path("subject_id") subjectId: Int,
        @Path("type") type: String
    ): List<TestResponse>

    @POST("content/practice/curriculumsubject/{lesson_id}/tests/{test_id}")
    suspend fun sendAnswer(
        @Body answer: TestAnswerRequest,
        @Path("lesson_id") lessonId: Long,
        @Path("test_id") testId: Int
    ): AnswerResultResponse

    //Profile
    @GET("profile/external/auth/me")
    suspend fun getProfile(): ProfileResponse

    @GET("profile/external/group/{id}/rating/leaderboard/short")
    suspend fun getShortList(@Path("id") groupId: Long): List<ShortPositionResponse>

    //Theory
    @GET("schedule/student/schedule/groups/{id}/curriculums?withKbs=1")
    suspend fun getAllTheory(@Path("id") id: Long): @kotlinx.serialization.Serializable Map<Long, List<TheoryResponse>>

    @GET("content/knowledgebase/{id}")
    suspend fun getKnowledgeBase(@Path("id") id: Int): TheoryItem

    @GET("schedule/student/schedule/groups/{id}/lessons?&withCurriculums=0&all=1")
    suspend fun getLessonsListWithType(
        @Path("id") id: Long,
        @Query("lessonType") lessonType: String
    ): LessonsResponse
}