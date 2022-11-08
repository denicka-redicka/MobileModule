package com.example.projectmaximummodule.data.debts.remote

import com.example.projectmaximummodule.data.network.retorfit.MainApiService
import com.example.projectmaximummodule.data.network.retorfit.request.ShowSolutionRequest
import com.example.projectmaximummodule.data.network.retorfit.request.TestAnswerRequest
import com.example.projectmaximummodule.util.runResultCatching
import javax.inject.Inject

class DebtsRemoteDataSourceImpl @Inject constructor(
    private val api: MainApiService
) : DebtsRemoteDataSource {

    private companion object {
        private const val PRACTICE = "practice"
    }

    override var type = ""

    override suspend fun getDebts(groupId: Long) = runResultCatching{
        val lessonWithPractice = api.getLessonsListWithType(groupId, PRACTICE)
        val debts = api.getDebts(groupId)

        lessonWithPractice.items.forEach { lessonWithPractice ->
            val lesson = debts.lessons[lessonWithPractice.id] ?: return@forEach
            debts.addDebtsItem(lesson)
        }
        return@runResultCatching debts
    }

    override suspend fun getHomeworkItems(subjectId: Long) = runResultCatching {
        api.getHomeworkCurriculumSubject(subjectId)
    }

    override suspend fun getTestsList(subjectId: Long, knowledgeBaseId: Int, type: String) =
        runResultCatching {
            api.getTests(subjectId, knowledgeBaseId, type)
        }

    override suspend fun sendAnswer(answer: TestAnswerRequest, subjectId: Long, testId: Int) = runResultCatching{
        api.sendAnswer(answer, subjectId, testId)
    }

    override suspend fun sendShowSolution(
        answer: ShowSolutionRequest,
        subjectId: Long,
        testId: Int
    ) = runResultCatching {
        api.sendShowSolution(answer, subjectId, testId)
    }

}