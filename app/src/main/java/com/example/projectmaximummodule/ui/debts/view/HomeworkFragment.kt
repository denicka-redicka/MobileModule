package com.example.projectmaximummodule.ui.debts.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.ui.debts.HomeworkViewModel
import com.example.projectmaximummodule.ui.debts.view.HomeworkItemFragment.Companion.CURRICULUM_SUBJECT_ID
import com.example.projectmaximummodule.ui.debts.view.HomeworkItemFragment.Companion.LESSON_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_homework.*

@AndroidEntryPoint
class HomeworkFragment : Fragment(R.layout.fragment_homework) {

    private val viewModel: HomeworkViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = HomeworkAdapter() { curriculumId, lessonId ->
            val bundle = Bundle()
            bundle.putString(CURRICULUM_SUBJECT_ID, curriculumId.toString())
            bundle.putString(LESSON_ID, lessonId.toString())
            findNavController().navigate(R.id.action_homeworkFragment_to_homeworkItemFragment, bundle)
        }
        debtsList.adapter = adapter
        debtsList.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)

        viewModel.debtsLiveData.observe(viewLifecycleOwner) { debts ->
            adapter.allDebtsCount = debts.allDebts - debts.paidDebts
            adapter.debts = debts.debtsItems
            adapter.notifyItemRangeChanged(2, debts.debtsItems.size)
        }

        if (savedInstanceState == null) {
            viewModel.getDebts()
        }

    }
}