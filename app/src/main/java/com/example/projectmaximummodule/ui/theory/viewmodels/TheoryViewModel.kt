package com.example.projectmaximummodule.ui.theory.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.projectmaximummodule.application.AppSharedPreferences
import com.example.projectmaximummodule.application.BaseViewModel
import com.example.projectmaximummodule.data.network.retorfit.MainApiService
import com.example.projectmaximummodule.data.network.retorfit.response.LessonsListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TheoryViewModel @Inject constructor(
    val api: MainApiService,
    val prefs: AppSharedPreferences
) : BaseViewModel() {

    companion object {
        private const val THEORY = "theory"
    }

    private var groupId = -1L

    private val mutableTheoryLiveData = MutableLiveData<List<LessonsListResponse.LessonResponse>>()
    val theoryLiveData: LiveData<List<LessonsListResponse.LessonResponse>> = mutableTheoryLiveData

    fun getAllTheory() {
        val idFromPrefs = prefs.getGroupId()
        if (theoryLiveData.value == null || groupId != idFromPrefs) {
            groupId = idFromPrefs
            if (groupId != -1L) {
                coroutineScope.launch {
                    val lessonsWithTheoryList = api.getLessonsListWithType(groupId, THEORY).items
                    val theoryMap = api.getAllTheory(groupId)
                    lessonsWithTheoryList.forEach { lesson ->
                        val theoryList = theoryMap.get(lesson.id)
                        if (theoryList != null)
                            lesson.submitTheoryTopicsList(theoryList)
                    }
                    mutableTheoryLiveData.postValue(lessonsWithTheoryList)
                }
            }
        }
    }
}