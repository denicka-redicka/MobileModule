package com.example.projectmaximummodule.ui.shedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.projectmaximummodule.application.AppSharedPreferences
import com.example.projectmaximummodule.application.BaseViewModel
import com.example.projectmaximummodule.data.network.retorfit.MainApiService
import com.example.projectmaximummodule.data.network.retorfit.response.LessonResponse
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

    private val lessonsMutableLiveData = MutableLiveData<List<LessonResponse>>()
    val lessonsLiveData: LiveData<List<LessonResponse>> = lessonsMutableLiveData

    var toDoList: List<ToDoResponse>? = null
    lateinit var teacher: TeacherResponse

    private suspend fun getToDoList(groupId: Long) {
        toDoList = api.getToDoList(groupId)
    }

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
            getToDoList(groupId)
            //val lessons = api.getLessons(groupId)
            val lessons = listOf<LessonResponse>()
            lessonsMutableLiveData.postValue(lessons)
        }
    }
}