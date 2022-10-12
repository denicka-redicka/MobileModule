package com.example.projectmaximummodule.ui.debts.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.ui.debts.HomeworkViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_homework.view.*

@AndroidEntryPoint
class HomeworkFragment: Fragment(R.layout.fragment_homework) {

    private val viewModel: HomeworkViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.debtsLiveData.observe(viewLifecycleOwner) { debts ->
            val adapter = HomeworkAdapter(debts.debtsItems, debts.allDebts - debts.paidDebts)
            view.debtsList.adapter = adapter
            view.debtsList.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        }

        if (savedInstanceState == null) {
            viewModel.getDebts()
        }

    }
}