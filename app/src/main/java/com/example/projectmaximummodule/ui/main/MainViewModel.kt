package com.example.projectmaximummodule.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.projectmaximummodule.application.AppSharedPreferences
import com.example.projectmaximummodule.application.BaseViewModel
import com.example.projectmaximummodule.application.SelectGroupReceiver.Companion.UNKNOWN_GROUP
import com.example.projectmaximummodule.data.network.retorfit.MainApiService
import com.example.projectmaximummodule.data.network.retorfit.response.GroupResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val api: MainApiService,
    val prefs: AppSharedPreferences): BaseViewModel() {

    private val groupsMutableLiveData = MutableLiveData<List<GroupResponse>>()
    val groupsLiveData: LiveData<List<GroupResponse>> = groupsMutableLiveData

    fun getGroupList() {
        coroutineScope.launch {
            val groups = api.getGroupsList()
            groupsMutableLiveData.postValue(groups)
        }
    }

    fun onGroupSelected(groupPosition: Int): Long {
        val selectedGroup = groupsLiveData.value?.get(groupPosition)?: return UNKNOWN_GROUP
        val teacher = selectedGroup.educationCourse.teacher
        prefs.setGroupId(selectedGroup.id)
        prefs.setTeacherName(teacher.fullName)
        prefs.setTeacherAvatar(teacher.userpick.toString())
        return groupsLiveData.value?.get(groupPosition)?.id?: UNKNOWN_GROUP
    }
}