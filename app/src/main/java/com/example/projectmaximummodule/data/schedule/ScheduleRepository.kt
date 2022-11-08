package com.example.projectmaximummodule.data.schedule

interface ScheduleRepository {

    var groupId: Long

    suspend fun fetchInfo()
}