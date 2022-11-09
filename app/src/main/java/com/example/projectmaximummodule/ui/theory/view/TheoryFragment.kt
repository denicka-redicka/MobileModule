package com.example.projectmaximummodule.ui.theory.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.ui.theory.viewmodels.TheoryViewModel
import com.example.projectmaximummodule.util.RemoteResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_theory.view.*

@AndroidEntryPoint
class TheoryFragment: Fragment(R.layout.fragment_theory) {

    private val viewModel: TheoryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.theoryLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is RemoteResult.Success -> {
                    val adapter = TheoryLessonAdapter(result.value) { topicId ->
                        moveToTheoryItemFragment(topicId)
                    }
                    adapter.setHasStableIds(true)
                    view.theoryLessonsList.adapter = adapter
                    view.theoryLessonsList.layoutManager =
                        LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
                    view.theoryLessonsList.setHasFixedSize(true)
                }
                is RemoteResult.Failed -> viewModel.showErrorUi(view, result.error)
            }

        }

        if (savedInstanceState == null) {
            viewModel.getAllTheory()
        }

    }

    private fun moveToTheoryItemFragment(topicId: Int) {
        val bundle = Bundle()
        bundle.putInt("topic_id", topicId)
        findNavController().navigate(R.id.action_theoryFragment_to_theoryItemFragment, bundle)
    }
}