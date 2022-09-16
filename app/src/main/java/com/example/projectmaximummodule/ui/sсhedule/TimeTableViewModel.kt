package com.example.projectmaximummodule.ui.sсhedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.projectmaximummodule.application.AppSharedPreferences
import com.example.projectmaximummodule.application.BaseViewModel
import com.example.projectmaximummodule.data.network.retorfit.MainApiService
import com.example.projectmaximummodule.data.network.retorfit.response.GroupStatisticsResponse
import com.example.projectmaximummodule.data.network.retorfit.response.ScheduleResponse
import com.example.projectmaximummodule.data.network.retorfit.response.TeacherResponse
import com.example.projectmaximummodule.data.network.retorfit.response.ToDoResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class TimeTableViewModel @Inject constructor (
    val api: MainApiService,
    val prefs: AppSharedPreferences
    ): BaseViewModel() {

    private var groupId = prefs.getGroupId()

    private val lessonsMutableLiveData = MutableLiveData<ScheduleResponse>()
    val lessonsLiveData: LiveData<ScheduleResponse> = lessonsMutableLiveData

    var toDoList: List<ToDoResponse>? = null
    lateinit var teacher: TeacherResponse
    lateinit var statistics: GroupStatisticsResponse

    fun setSelectedGroup(id: Long) {
        if (groupId != id) {
            groupId = id
            fetchInfo()
        } else {
            return
        }
    }

    fun updateScheduleList() {
        if (lessonsLiveData.value == null) {
            fetchInfo()
        } else if (groupId != prefs.getGroupId()) {
            groupId = prefs.getGroupId()
            fetchInfo()
        }
    }

    private fun fetchInfo() {
        teacher = TeacherResponse(
            prefs.getTeacherName()?: "",
            0,
            prefs.getTeacherAvatar()
        )
        coroutineScope.launch {
            statistics = api.getStatistics(groupId)
            toDoList = api.getToDoList(groupId)
            val lessons = api.getLessons(groupId)
            lessonsMutableLiveData.postValue(lessons)
        }
    }
}