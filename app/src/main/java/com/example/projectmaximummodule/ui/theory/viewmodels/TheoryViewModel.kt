package com.example.projectmaximummodule.ui.theory.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.projectmaximummodule.application.AppSharedPreferences
import com.example.projectmaximummodule.application.BaseViewModel
import com.example.projectmaximummodule.data.network.retorfit.MainApiService
import com.example.projectmaximummodule.data.network.retorfit.response.LessonsResponse
import com.example.projectmaximummodule.data.network.retorfit.response.TheoryResponse
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

    var lessonsWithTheoryList: List<LessonsResponse.LessonResponse> = listOf()

    private val mutableTheoryLiveData = MutableLiveData<Map<Long, List<TheoryResponse>>>()

    val theoryLiveData: LiveData<Map<Long, List<TheoryResponse>>> = mutableTheoryLiveData

    fun getAllTheory() {
        groupId = prefs.getGroupId()
        if (groupId != -1L) {
            coroutineScope.launch {
                lessonsWithTheoryList = api.getLessonsListWithType(groupId, THEORY).items
                val theoryMap = api.getAllTheory(groupId)
                theoryMap.forEach {

                }
                mutableTheoryLiveData.postValue(theoryMap)
            }
        }

    }
}