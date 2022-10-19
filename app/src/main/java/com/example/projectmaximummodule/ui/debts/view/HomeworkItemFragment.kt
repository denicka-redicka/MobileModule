package com.example.projectmaximummodule.ui.debts.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.ui.debts.HomeworkItemViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_homework_items.view.*

@AndroidEntryPoint
class HomeworkItemFragment: Fragment(R.layout.fragment_homework_items),
    AdapterView.OnItemSelectedListener {

    private val viewModel: HomeworkItemViewModel by viewModels()
    private var curriculumSubjectId = -1L
    private var parentLessonId = -1L

    companion object {
        const val CURRICULUM_SUBJECT_ID = "curriculum_subject_id"
        const val LESSON_ID = "lesson_id"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        curriculumSubjectId = arguments?.getLong(CURRICULUM_SUBJECT_ID) ?: -1
        parentLessonId = arguments?.getLong(LESSON_ID) ?: -1

        val adapter = HomeworkExerciseAdapter()
        view.itemsPageView.adapter = adapter


        viewModel.baseLiveData.observe(viewLifecycleOwner) { base ->
            viewModel.updateUi(view)
        }

        viewModel.testLiveData.observe(viewLifecycleOwner) { tests ->
            adapter.submitList(tests)
        }

        view.subjectsPicker.onItemSelectedListener = this

        if (savedInstanceState == null) {
            viewModel.fetchHomeworksItems(curriculumSubjectId)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, itemId: Long) {
        viewModel.fetchTestsList(curriculumSubjectId, position)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
}