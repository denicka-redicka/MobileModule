package com.example.projectmaximummodule.data.schedule.local

import com.example.projectmaximummodule.data.network.retorfit.response.TeacherResponse

interface ScheduleLocalDataSource {

    fun getTeacherInfo(): TeacherResponse
}