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
    private var id = -1L

    companion object {
        const val CURRICULUM_SUBJECT_ID = "curriculum_subject_id"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        id = arguments?.getLong(CURRICULUM_SUBJECT_ID) ?: -1

        val adapter = HomeworkExerciseAdapter()
        view.itemsPageView.adapter = adapter


        viewModel.baseLiveData.observe(viewLifecycleOwner) { base ->
            viewModel.updateUi(view)
        }

        viewModel.testLiveData.observe(viewLifecycleOwner) { tests ->
            adapter.testsList = tests
            adapter.notifyDataSetChanged()
        }

        view.subjectsPicker.onItemSelectedListener = this

        if (savedInstanceState == null) {
            viewModel.fetchHomeworksItems(id)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, itemId: Long) {
        viewModel.fetchTestsList(id, position)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
}