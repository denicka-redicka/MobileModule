package com.example.projectmaximummodule.ui.debts

import android.R
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.projectmaximummodule.application.BaseViewModel
import com.example.projectmaximummodule.data.debts.DebtsRepository
import com.example.projectmaximummodule.data.network.retorfit.request.ShowSolutionRequest
import com.example.projectmaximummodule.data.network.retorfit.request.TestAnswerRequest
import com.example.projectmaximummodule.data.network.retorfit.response.AnswerResultResponse
import com.example.projectmaximummodule.data.network.retorfit.response.HomeworkCurriculumSubjectResponse
import com.example.projectmaximummodule.data.network.retorfit.response.TestResponse
import com.example.projectmaximummodule.data.network.retorfit.response.TreeResponse
import com.example.projectmaximummodule.util.RemoteResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.android.synthetic.main.fragment_homework_items.view.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeworkItemViewModel @Inject constructor(
    private val repository: DebtsRepository
) : BaseViewModel() {

    companion object {
        const val AFTER_THEORY = "aftertheory"
        const val OFFLINE = "offline"
        const val ONLINE = "online"
    }

    private val mutableCurriculumSubjectLiveData = MutableLiveData<RemoteResult<HomeworkCurriculumSubjectResponse, Throwable>>()
    val curriculumSubjectLiveData: LiveData<RemoteResult<HomeworkCurriculumSubjectResponse, Throwable>> = mutableCurriculumSubjectLiveData

    private val mutableTestsLiveData = MutableLiveData<RemoteResult<List<TestResponse>, Throwable>>()
    val testsLiveData: LiveData<RemoteResult<List<TestResponse>, Throwable>> = mutableTestsLiveData

    var curriculumSubjectId = -1L
    private var homeworkTitle = ""
    private var knowledgeBaseList: List<TreeResponse.KnowledgeBaseInfo> = listOf()
    private var tests: List<TestResponse> = listOf()

    fun fetchHomeworksItems() {
        if (curriculumSubjectId != -1L) {
            coroutineScope.launch {
                val result = repository.getHomeworkItems(curriculumSubjectId)
                if (result is RemoteResult.Success) {
                    val subjects = result.value
                    homeworkTitle = subjects.curriculumSubjects.title
                    when {
                        subjects.tree.aftertheory.isNotEmpty() -> {
                            repository.setDebtsType(AFTER_THEORY)
                            knowledgeBaseList = subjects.tree.aftertheory
                        }
                        subjects.tree.offline.isNotEmpty() -> {
                            repository.setDebtsType(OFFLINE)
                            knowledgeBaseList = subjects.tree.offline
                        }
                        subjects.tree.online.isNotEmpty() -> {
                            repository.setDebtsType(ONLINE)
                            knowledgeBaseList = subjects.tree.online
                        }
                    }
                }

                mutableCurriculumSubjectLiveData.postValue(result)
            }
        }
    }

    fun fetchTestsList(position: Int) {
        coroutineScope.launch {
            val result = repository.getTestList(curriculumSubjectId, knowledgeBaseList[position].id)
            if (result is RemoteResult.Success)
                tests = result.value
            mutableTestsLiveData.postValue(result)
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

    fun sendAnswer(answer: TestAnswerRequest, testId: Int) {
        coroutineScope.launch {
            val result = repository.sendAnswer(answer, curriculumSubjectId, testId)
            onAnswerResultReturn(result, testId)
        }
    }

    fun sendShowSolution(answer: ShowSolutionRequest, testId: Int) {
        coroutineScope.launch {
            val result = repository.sendShowSolution(answer, curriculumSubjectId, testId)
            onAnswerResultReturn(result, testId)
        }
    }

    private fun onAnswerResultReturn(
        result: RemoteResult<AnswerResultResponse, Throwable>,
        testId: Int
    ) {
        if (result is RemoteResult.Success) {
            tests.forEach { test ->
                if (test.id == testId) test.studentTestResult = result.value
            }
            mutableTestsLiveData.postValue(RemoteResult.Success(tests))
        }
    }
}