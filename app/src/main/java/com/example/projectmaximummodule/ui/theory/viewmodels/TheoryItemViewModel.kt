package com.example.projectmaximummodule.ui.theory.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.projectmaximummodule.application.AppSharedPreferences
import com.example.projectmaximummodule.application.BaseViewModel
import com.example.projectmaximummodule.data.network.retorfit.MainApiService
import com.example.projectmaximummodule.data.network.retorfit.response.TheoryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.android.synthetic.main.fragment_theory_item.view.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TheoryItemViewModel @Inject constructor(
    val api: MainApiService,
    val prefs: AppSharedPreferences
): BaseViewModel() {

    private val mutableTheoryItemLiveData = MutableLiveData<TheoryItem>()

    val theoryItemLiveData: LiveData<TheoryItem> = mutableTheoryItemLiveData

    fun loadTheory(topicId: Int) {
        coroutineScope.launch {
            val item = api.getKnowledgeBase(topicId)
            mutableTheoryItemLiveData.postValue(item)
        }
    }

    fun setupUi(view: View, item: TheoryItem) {

        view.theoryItemHeader.text = item.title
        view.theoryHtmlView.loadData(item.body,  "text/html", "UTF-8")
    }
}