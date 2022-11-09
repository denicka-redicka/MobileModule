package com.example.projectmaximummodule.ui.theory.viewmodels

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.projectmaximummodule.application.AppSharedPreferences
import com.example.projectmaximummodule.application.BaseViewModel
import com.example.projectmaximummodule.data.network.retorfit.MainApiService
import com.example.projectmaximummodule.data.network.retorfit.response.LessonsListResponse
import com.example.projectmaximummodule.data.theory.TheoryRepository
import com.example.projectmaximummodule.util.RemoteResult
import com.example.projectmaximummodule.util.toGone
import com.example.projectmaximummodule.util.toVisible
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.android.synthetic.main.fragment_theory.view.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TheoryViewModel @Inject constructor(
    private val repository: TheoryRepository,
    val api: MainApiService,
    val prefs: AppSharedPreferences
) : BaseViewModel() {

    private var groupId = -1L

    private val mutableTheoryLiveData = MutableLiveData<RemoteResult<List<LessonsListResponse.LessonResponse>, Throwable>>()
    val theoryLiveData: LiveData<RemoteResult<List<LessonsListResponse.LessonResponse>, Throwable>> = mutableTheoryLiveData

    fun getAllTheory() {
        val idFromPrefs = prefs.getGroupId()
        if (theoryLiveData.value == null || groupId != idFromPrefs) {
            groupId = idFromPrefs
            if (groupId != -1L) {
                coroutineScope.launch {
                    mutableTheoryLiveData.postValue(repository.getAllData(groupId))
                }
            }
        }
    }

    fun showErrorUi(view: View, exception: Throwable) {
        view.theoryLessonsList.toGone()
        view.theoryErrorText.toVisible()
        Log.e("Theory exception", "Exception handled: ${exception.message}")
    }
}