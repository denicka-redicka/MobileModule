package com.example.projectmaximummodule.data.schedule.remote

import com.example.projectmaximummodule.data.network.retorfit.response.GroupStatisticsResponse
import com.example.projectmaximummodule.data.network.retorfit.response.LessonsListResponse
import com.example.projectmaximummodule.data.network.retorfit.response.ToDoResponse
import com.example.projectmaximummodule.util.RemoteResult

interface ScheduleRemoteDataSource {

    suspend fun getStatistics(id: Long): RemoteResult<GroupStatisticsResponse, Throwable>

    suspend fun getToDoList(id: Long): RemoteResult<List<ToDoResponse>, Throwable>

    suspend fun getLessonsList(id: Long): RemoteResult<LessonsListResponse, Throwable>
}