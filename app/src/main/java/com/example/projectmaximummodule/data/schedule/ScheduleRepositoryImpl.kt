package com.example.projectmaximummodule.data.schedule

import com.example.projectmaximummodule.data.schedule.local.ScheduleLocalDataSource
import com.example.projectmaximummodule.data.schedule.remote.ScheduleRemoteDataSource
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    private val remoteDataSource: ScheduleRemoteDataSource,
    private val localDataSource: ScheduleLocalDataSource
) : ScheduleRepository {

    override var groupId: Long = -1

    override suspend fun fetchInfo() {
        val teacherInfo = localDataSource.getTeacherInfo()
        val statistics = remoteDataSource.getStatistics(groupId)
        val toDoList = remoteDataSource.getToDoList(groupId)
        val lessonsList = remoteDataSource.getLessonsList(groupId)
    }
}