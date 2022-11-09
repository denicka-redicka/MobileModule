package com.example.projectmaximummodule.data.schedule.local

import com.example.projectmaximummodule.core.application.AppSharedPreferences
import com.example.projectmaximummodule.data.network.retorfit.response.TeacherResponse
import javax.inject.Inject

class ScheduleLocalDataSourceImpl @Inject constructor(
    private val prefs: AppSharedPreferences
): ScheduleLocalDataSource {

    override fun getTeacherInfo(): TeacherResponse {
        return TeacherResponse(
            fullName = prefs.getTeacherName()?: "",
            id = 0,
            userpick = prefs.getTeacherAvatar()
        )
    }
}