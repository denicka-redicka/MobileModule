package com.example.projectmaximummodule.ui.theory.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.ui.theory.viewmodels.TheoryItemViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TheoryItemFragment: Fragment(R.layout.fragment_theory_item) {

    val viewModel: TheoryItemViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.theoryItemLiveData.observe(viewLifecycleOwner) { item ->
            viewModel.setupUi(view, item)
        }

        if (savedInstanceState == null) {
            val topicId = arguments?.getInt("topic_id")?: -1
            viewModel.loadTheory(topicId)
        }

    }
}