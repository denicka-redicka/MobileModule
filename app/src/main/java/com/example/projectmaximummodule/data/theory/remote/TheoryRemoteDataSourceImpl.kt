package com.example.projectmaximummodule.data.theory.remote

import com.example.projectmaximummodule.data.network.retorfit.MainApiService
import com.example.projectmaximummodule.util.runResultCatching
import javax.inject.Inject

class TheoryRemoteDataSourceImpl @Inject constructor(
    private val api: MainApiService
): TheoryRemoteDataSource{

    companion object {
        private const val THEORY = "theory"
    }

    override suspend fun getAllAllTheory(groupId: Long) = runResultCatching {
        val lessonsWithTheoryList = api.getLessonsListWithType(groupId, THEORY).items
        val theoryMap = api.getAllTheory(groupId)
        lessonsWithTheoryList.forEach { lesson ->
            val theoryList = theoryMap.get(lesson.id)
            if (theoryList != null)
                lesson.submitTheoryTopicsList(theoryList)
        }
        return@runResultCatching lessonsWithTheoryList
    }

    override suspend fun getTheoryItem(topicId: Int) = runResultCatching {
        api.getKnowledgeBase(topicId)
    }
}