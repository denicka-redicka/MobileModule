package com.example.projectmaximummodule.ui.debts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.projectmaximummodule.application.AppSharedPreferences
import com.example.projectmaximummodule.application.BaseViewModel
import com.example.projectmaximummodule.data.debts.DebtsRepository
import com.example.projectmaximummodule.data.network.retorfit.response.DebtsResponse
import com.example.projectmaximummodule.util.RemoteResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeworkViewModel @Inject constructor(
    private val repository: DebtsRepository,
    private val prefs: AppSharedPreferences,
): BaseViewModel() {

    private val mutableDebtsLiveData = MutableLiveData<RemoteResult<DebtsResponse, Throwable>>()
    val debtsLiveData: LiveData<RemoteResult<DebtsResponse, Throwable>> = mutableDebtsLiveData

    private var groupId = -1L

    fun getDebts() {
        val idFromPrefs = prefs.getGroupId()
        if (debtsLiveData.value == null || groupId != idFromPrefs) {
            groupId = idFromPrefs
            coroutineScope.launch {
                mutableDebtsLiveData.postValue(repository.getDebts(groupId))
            }

        }
    }
}