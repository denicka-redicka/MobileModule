package com.example.projectmaximummodule.data.theory

import com.example.projectmaximummodule.data.network.retorfit.response.LessonsListResponse
import com.example.projectmaximummodule.data.network.retorfit.response.TheoryItem
import com.example.projectmaximummodule.util.RemoteResult

interface TheoryRepository {

    suspend fun getAllData(groupId: Long): RemoteResult<List<LessonsListResponse.LessonResponse>, Throwable>

    suspend fun getTheoryItem(topicId: Int): RemoteResult<TheoryItem, Throwable>
}