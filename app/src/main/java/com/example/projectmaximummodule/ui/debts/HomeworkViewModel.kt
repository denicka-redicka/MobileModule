package com.example.projectmaximummodule.ui.debts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.projectmaximummodule.application.AppSharedPreferences
import com.example.projectmaximummodule.application.BaseViewModel
import com.example.projectmaximummodule.data.network.retorfit.MainApiService
import com.example.projectmaximummodule.data.network.retorfit.response.DebtsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeworkViewModel @Inject constructor(
    private val prefs: AppSharedPreferences,
    private val api: MainApiService
): BaseViewModel() {

    private companion object {
        private const val PRACTICE = "practice"
    }

    private val mutableDebtsLiveData = MutableLiveData<DebtsResponse>()
    val debtsLiveData: LiveData<DebtsResponse> = mutableDebtsLiveData

    private var groupId = -1L

    fun getDebts() {
        val idFromPrefs = prefs.getGroupId()
        if (debtsLiveData.value == null || groupId != idFromPrefs) {
            groupId = idFromPrefs
            coroutineScope.launch {
                val lessonWithPractice = api.getLessonsListWithType(groupId, PRACTICE)
                val debts = api.getDebts(groupId)

                lessonWithPractice.items.forEach { lessonWithPractice ->
                    val lesson = debts.lessons[lessonWithPractice.id]?: return@forEach
                    debts.addDebtsItem(lesson)
                }
                mutableDebtsLiveData.postValue(debts)
            }

        }
    }
}