package com.example.projectmaximummodule.data.debts

import com.example.projectmaximummodule.data.network.retorfit.request.ShowSolutionRequest
import com.example.projectmaximummodule.data.network.retorfit.request.TestAnswerRequest
import com.example.projectmaximummodule.data.network.retorfit.response.AnswerResultResponse
import com.example.projectmaximummodule.data.network.retorfit.response.DebtsResponse
import com.example.projectmaximummodule.data.network.retorfit.response.HomeworkCurriculumSubjectResponse
import com.example.projectmaximummodule.data.network.retorfit.response.TestResponse
import com.example.projectmaximummodule.util.RemoteResult

interface DebtsRepository {

    fun setDebtsType(type: String)

    suspend fun getDebts(groupId: Long): RemoteResult<DebtsResponse, Throwable>

    suspend fun getHomeworkItems(subjectId: Long): RemoteResult<HomeworkCurriculumSubjectResponse, Throwable>

    suspend fun getTestList(
        groupId: Long,
        subjectId: Int
    ): RemoteResult<List<TestResponse>, Throwable>

    suspend fun sendAnswer(
        answer: TestAnswerRequest,
        subjectId: Long,
        testId: Int
    ): RemoteResult<AnswerResultResponse, Throwable>

    suspend fun sendShowSolution(
        answer: ShowSolutionRequest,
        subjectId: Long,
        testId: Int
    ): RemoteResult<AnswerResultResponse, Throwable>
}