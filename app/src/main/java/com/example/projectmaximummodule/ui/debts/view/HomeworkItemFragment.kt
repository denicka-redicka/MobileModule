package com.example.projectmaximummodule.ui.debts.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.data.network.retorfit.request.ShowSolutionRequest
import com.example.projectmaximummodule.data.network.retorfit.request.TestAnswerRequest
import com.example.projectmaximummodule.data.network.retorfit.response.TestResponse
import com.example.projectmaximummodule.ui.debts.HomeworkItemViewModel
import com.example.projectmaximummodule.util.RemoteResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_homework_items.view.*

@AndroidEntryPoint
class HomeworkItemFragment: Fragment(R.layout.fragment_homework_items),
    AdapterView.OnItemSelectedListener, HomeworkExerciseAdapter.OnButtonsClickListener {

    private val viewModel: HomeworkItemViewModel by viewModels()
    private var parentLessonId = -1L
    private var currentExercisePosition = -1

    companion object {
        const val CURRICULUM_SUBJECT_ID = "curriculum_subject_id"
        const val LESSON_ID = "lesson_id"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.curriculumSubjectId = arguments?.getString(CURRICULUM_SUBJECT_ID)?.toLong()?: -1
        parentLessonId = arguments?.getString(LESSON_ID)?.toLong()?: -1

        val callBack = ExercisePageChangedCallback()
        val adapter = HomeworkExerciseAdapter(parentLessonId, this, callBack)
        view.itemsPageView.adapter = adapter
        view.itemsPageView.registerOnPageChangeCallback(callBack)


        viewModel.curriculumSubjectLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is RemoteResult.Success -> viewModel.updateUi(view)
                is RemoteResult.Failed -> Toast.makeText(context, "Something goes wrong", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.testsLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is RemoteResult.Success -> updateTestsList(adapter, result.value)
                is RemoteResult.Failed -> Toast.makeText(context, "Something goes wrong", Toast.LENGTH_SHORT).show()
            }
        }

        view.subjectsPicker.onItemSelectedListener = this

        if (savedInstanceState == null) {
            viewModel.fetchHomeworksItems()
        }
    }

    private fun updateTestsList(adapter: HomeworkExerciseAdapter, tests: List<TestResponse>, currentPosition: Int = currentExercisePosition) {
        adapter.submitList(tests)
        if (currentPosition != -1) {
            adapter.notifyItemChanged(currentExercisePosition)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, itemId: Long) {
        viewModel.fetchTestsList(position)
    }

    override fun onNothingSelected(view: AdapterView<*>?) {
    }

    override fun onAnswerButtonClick(answer: TestAnswerRequest, testId: Int, exercisePosition: Int) {
        currentExercisePosition = exercisePosition
        viewModel.sendAnswer(answer, testId)
    }

    override fun onSolutionButtonClick(answer: ShowSolutionRequest, testId: Int, exercisePosition: Int) {
        currentExercisePosition = exercisePosition
        viewModel.sendShowSolution(answer, testId)
    }
}