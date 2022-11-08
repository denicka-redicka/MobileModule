package com.example.projectmaximummodule.data.theory

import com.example.projectmaximummodule.data.network.retorfit.response.LessonsListResponse
import com.example.projectmaximummodule.data.network.retorfit.response.TheoryItem
import com.example.projectmaximummodule.data.theory.remote.TheoryRemoteDataSource
import com.example.projectmaximummodule.util.RemoteResult
import javax.inject.Inject

class TheoryRepositoryImpl @Inject constructor(
    private val remoteDataSource: TheoryRemoteDataSource
): TheoryRepository {

    override suspend fun getAllData(groupId: Long): RemoteResult<List<LessonsListResponse.LessonResponse>, Throwable> {
        return remoteDataSource.getAllAllTheory(groupId)
    }

    override suspend fun getTheoryItem(topicId: Int): RemoteResult<TheoryItem, Throwable> {
        return remoteDataSource.getTheoryItem(topicId)
    }
}