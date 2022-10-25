package com.example.projectmaximummodule.ui.debts

import android.R
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.projectmaximummodule.application.BaseViewModel
import com.example.projectmaximummodule.data.network.retorfit.MainApiService
import com.example.projectmaximummodule.data.network.retorfit.request.TestAnswerRequest
import com.example.projectmaximummodule.data.network.retorfit.response.HomeworkCurriculumSubjectResponse
import com.example.projectmaximummodule.data.network.retorfit.response.TestResponse
import com.example.projectmaximummodule.data.network.retorfit.response.TreeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.android.synthetic.main.fragment_homework_items.view.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeworkItemViewModel @Inject constructor(
    private val api: MainApiService
) : BaseViewModel() {

    companion object {
        const val AFTER_THEORY = "aftertheory"
        const val OFFLINE = "offline"
        const val ONLINE = "online"
    }

    private val mutableBaseLiveData = MutableLiveData<HomeworkCurriculumSubjectResponse>()
    val baseLiveData: LiveData<HomeworkCurriculumSubjectResponse> = mutableBaseLiveData

    private val mutableTestLiveData = MutableLiveData<List<TestResponse>>()
    val testLiveData: LiveData<List<TestResponse>> = mutableTestLiveData

    private var homeworkTitle = ""
    private var type = ""
    private var knowledgeBaseList: List<TreeResponse.KnowledgeBaseInfo> = listOf()
    private var tests: List<TestResponse> = listOf()

    fun fetchHomeworksItems(id: Long) {
        if (id != -1L) {
            coroutineScope.launch {
                val subjects = api.getHomeworkCurriculumSubject(id)
                homeworkTitle = subjects.curriculumSubjects.title
                when {
                    subjects.tree.aftertheory.isNotEmpty() -> {
                        type = AFTER_THEORY
                        knowledgeBaseList = subjects.tree.aftertheory
                    }
                    subjects.tree.offline.isNotEmpty() -> {
                        type = OFFLINE
                        knowledgeBaseList = subjects.tree.offline
                    }
                    subjects.tree.online.isNotEmpty() -> {
                        type = ONLINE
                        knowledgeBaseList = subjects.tree.online
                    }
                }
                mutableBaseLiveData.postValue(subjects)
            }
        }
    }

    fun fetchTestsList(id: Long, position: Int) {
        coroutineScope.launch {
            tests = api.getTests(id, knowledgeBaseList[position].id, type)
            mutableTestLiveData.postValue(tests)
        }
    }

    fun updateUi(view: View) {
        view.exerciseTitle.text = homeworkTitle
        val knowledgeBaseTitles = knowledgeBaseList.map { info ->
            info.title
        }
        val adapter = ArrayAdapter(view.context, R.layout.simple_list_item_1, knowledgeBaseTitles)
        view.subjectsPicker.adapter = adapter

    }

    fun sendAnswer(answer: TestAnswerRequest, lessonId: Long, testId: Int) {
        coroutineScope.launch {
            val answer = api.sendAnswer(answer, lessonId, testId)
            tests.forEach { test ->
                if (test.id == testId) test.studentTestResult = answer
            }
            mutableTestLiveData.postValue(tests)
        }
    }
}