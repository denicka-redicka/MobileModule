package com.example.projectmaximummodule.ui.theory.viewmodels

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.projectmaximummodule.core.application.BaseViewModel
import com.example.projectmaximummodule.data.network.retorfit.response.TheoryItem
import com.example.projectmaximummodule.data.theory.TheoryRepository
import com.example.projectmaximummodule.util.RemoteResult
import com.example.projectmaximummodule.util.toGone
import com.example.projectmaximummodule.util.toVisible
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.android.synthetic.main.fragment_theory_item.view.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TheoryItemViewModel @Inject constructor(
    private val repository: TheoryRepository,
): BaseViewModel() {

    private val mutableTheoryItemLiveData = MutableLiveData<RemoteResult<TheoryItem, Throwable>>()
    val theoryItemLiveData: LiveData<RemoteResult<TheoryItem, Throwable>> = mutableTheoryItemLiveData

    fun loadTheory(topicId: Int) {
        coroutineScope.launch {
            mutableTheoryItemLiveData.postValue(repository.getTheoryItem(topicId))
        }
    }

    fun setupUi(view: View, item: TheoryItem) {
        view.theoryItemHeader.text = item.title
        view.theoryHtmlView.loadData(item.body,  "text/html", "UTF-8")
    }

    fun showErrorUi(view: View, error: Throwable) {
        view.theoryItemHeader.toGone()
        view.theoryHtmlView.toGone()
        view.theoryItemErrorText.toVisible()
        Log.e("Theory item exception", "Exception handled: ${error.message}")
    }
}