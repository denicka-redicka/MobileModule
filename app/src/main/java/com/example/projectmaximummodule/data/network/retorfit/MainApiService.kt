package com.example.projectmaximummodule.data.network.retorfit

import com.example.projectmaximummodule.data.network.retorfit.request.MessageRequest
import com.example.projectmaximummodule.data.auth.remote.request.OauthRequest
import com.example.projectmaximummodule.data.network.retorfit.request.ShowSolutionRequest
import com.example.projectmaximummodule.data.network.retorfit.request.TestAnswerRequest
import com.example.projectmaximummodule.data.network.retorfit.response.*
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MainApiService {

    @GET("profile/external/auth/me")
    suspend fun getInfoAboutMe(): AboutMeResponse

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
    suspend fun getLessons(@Path("id") groupId: Long): LessonsListResponse

    @GET("schedule/student/schedule/groups/{id}/todolist")
    suspend fun getToDoList(@Path("id") groupId: Long): List<ToDoResponse>

    @GET("profile/external/group/{id}/statistics")
    suspend fun getStatistics(@Path("id") groupId: Long): GroupStatisticsResponse

    //Homework
    @GET("schedule/student/schedule/groups/{id}/debts")
    suspend fun getDebts(@Path("id") groupId: Long): DebtsResponse

    @GET("content/practice/curriculumsubject/{id}")
    suspend fun getHomeworkCurriculumSubject(@Path("id") id: Long): HomeworkCurriculumSubjectResponse

    @GET("content/practice/curriculumsubject/{group_id}/knowledgebase/{curriculum_subject_id}/{type}/tests")
    suspend fun getTests(
        @Path("group_id") groupId: Long,
        @Path("curriculum_subject_id") subjectId: Int,
        @Path("type") type: String
    ): List<TestResponse>

    @POST("content/practice/curriculumsubject/{curriculum_subject_id}/tests/{test_id}")
    suspend fun sendAnswer(
        @Body answer: TestAnswerRequest,
        @Path("curriculum_subject_id") lessonId: Long,
        @Path("test_id") testId: Int
    ): AnswerResultResponse

    @POST("content/practice/curriculumsubject/{curriculum_subject_id}/tests/{test_id}")
    suspend fun sendShowSolution(
        @Body answer: ShowSolutionRequest,
        @Path("curriculum_subject_id") lessonId: Long,
        @Path("test_id") testId: Int
    ): AnswerResultResponse

    //Messenger
    @GET("chat/dialogs")
    suspend fun getAllChats(): List<ChatInfoResponse>

    @GET("chat/dialogs/{chatId}")
    suspend fun getChat(@Path("chatId") chatId: Long): ChatBodyResponse

    @GET("chat/dialogs/unread")
    suspend fun getUnreadCount(): @Serializable Int

    @PATCH("chat/dialogs/{chatId}/viewed")
    suspend fun setChatViewed(@Path("chatId") chatId: Long)

    @POST("chat/dialogs/{chatId}")
    suspend fun sendMessage(@Body message: MessageRequest, @Path("chatId") chatId: Long)

    //Profile
    @GET("profile/external/auth/me")
    suspend fun getProfile(): ProfileResponse

    @GET("profile/external/group/{id}/rating/leaderboard/short")
    suspend fun getShortList(@Path("id") groupId: Long): List<ShortPositionResponse>

    //Theory
    @GET("schedule/student/schedule/groups/{id}/curriculums?withKbs=1")
    suspend fun getAllTheory(@Path("id") id: Long): @Serializable Map<Long, List<TheoryResponse>>

    @GET("content/knowledgebase/{id}")
    suspend fun getKnowledgeBase(@Path("id") id: Int): TheoryItem

    @GET("schedule/student/schedule/groups/{id}/lessons?&withCurriculums=0&all=1")
    suspend fun getLessonsListWithType(
        @Path("id") id: Long,
        @Query("lessonType") lessonType: String
    ): LessonsListResponse
}