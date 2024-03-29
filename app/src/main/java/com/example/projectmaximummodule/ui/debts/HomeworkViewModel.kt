package com.example.projectmaximummodule.ui.debts

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.projectmaximummodule.core.application.AppSharedPreferences
import com.example.projectmaximummodule.core.application.BaseViewModel
import com.example.projectmaximummodule.data.debts.DebtsRepository
import com.example.projectmaximummodule.data.network.retorfit.response.DebtsResponse
import com.example.projectmaximummodule.util.RemoteResult
import com.example.projectmaximummodule.util.toGone
import com.example.projectmaximummodule.util.toVisible
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.android.synthetic.main.fragment_homework.view.*
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

    fun showErrorUi(view: View) {
        view.debtsList.toGone()
        view.debtsErrorText.toVisible()
    }
}