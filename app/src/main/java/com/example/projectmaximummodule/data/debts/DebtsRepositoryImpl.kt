package com.example.projectmaximummodule.data.debts

import com.example.projectmaximummodule.data.debts.remote.DebtsRemoteDataSource
import com.example.projectmaximummodule.data.network.retorfit.request.ShowSolutionRequest
import com.example.projectmaximummodule.data.network.retorfit.request.TestAnswerRequest
import com.example.projectmaximummodule.data.network.retorfit.response.AnswerResultResponse
import com.example.projectmaximummodule.data.network.retorfit.response.DebtsResponse
import com.example.projectmaximummodule.data.network.retorfit.response.HomeworkCurriculumSubjectResponse
import com.example.projectmaximummodule.data.network.retorfit.response.TestResponse
import com.example.projectmaximummodule.util.RemoteResult
import javax.inject.Inject

class DebtsRepositoryImpl @Inject constructor(
    private val remoteDataSource: DebtsRemoteDataSource
): DebtsRepository {

    override fun setDebtsType(type: String) {
        remoteDataSource.type = type
    }

    override suspend fun getDebts(groupId: Long): RemoteResult<DebtsResponse, Throwable> {
        return remoteDataSource.getDebts(groupId)
    }

    override suspend fun getHomeworkItems(subjectId: Long): RemoteResult<HomeworkCurriculumSubjectResponse, Throwable> {
        return remoteDataSource.getHomeworkItems(subjectId)
    }

    override suspend fun getTestList(groupId: Long, subjectId: Int): RemoteResult<List<TestResponse>, Throwable> {
        return remoteDataSource.getTestsList(groupId, subjectId)
    }

    override suspend fun sendAnswer(answer: TestAnswerRequest, subjectId: Long, testId: Int): RemoteResult<AnswerResultResponse, Throwable> {
        return remoteDataSource.sendAnswer(answer, subjectId, testId)
    }

    override suspend fun sendShowSolution(answer: ShowSolutionRequest, subjectId: Long, testId: Int): RemoteResult<AnswerResultResponse, Throwable> {
        return remoteDataSource.sendShowSolution(answer,subjectId, testId)
    }
}