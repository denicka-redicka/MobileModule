package com.example.projectmaximummodule.data.theory.remote

import com.example.projectmaximummodule.data.network.retorfit.response.LessonsListResponse
import com.example.projectmaximummodule.data.network.retorfit.response.TheoryItem
import com.example.projectmaximummodule.util.RemoteResult

interface TheoryRemoteDataSource {

    suspend fun getAllAllTheory(groupId: Long): RemoteResult<List<LessonsListResponse.LessonResponse>, Throwable>

    suspend fun getTheoryItem(topicId: Int): RemoteResult<TheoryItem, Throwable>
}