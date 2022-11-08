package com.example.projectmaximummodule.data.schedule.remote

import com.example.projectmaximummodule.data.network.retorfit.MainApiService
import com.example.projectmaximummodule.util.runResultCatching
import javax.inject.Inject


class ScheduleRemoteDataSourceImpl @Inject constructor(
    private val apiService: MainApiService,
): ScheduleRemoteDataSource {

    override suspend fun getStatistics(id: Long) = runResultCatching {
        apiService.getStatistics(id)
    }

    override suspend fun getToDoList(id: Long)= runResultCatching {
        apiService.getToDoList(id)
    }

    override suspend fun getLessonsList(id: Long)= runResultCatching {
        apiService.getLessons(id)
    }
}