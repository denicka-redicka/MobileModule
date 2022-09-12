package com.example.projectmaximummodule.ui.shedule.view

import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.application.SelectGroupReceiver
import com.example.projectmaximummodule.application.SelectGroupReceiver.Companion.GROUP_SELECTED
import com.example.projectmaximummodule.ui.shedule.TimeTableViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_shedule.view.*

@AndroidEntryPoint
class TimeTableFragment: Fragment(R.layout.fragment_shedule), SelectGroupReceiver.OnGroupSelectedListener {

    private val viewModel: TimeTableViewModel by viewModels()
    private lateinit var selectGroupReceiver: SelectGroupReceiver


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.lessonsLiveData.observe(viewLifecycleOwner) { lessons ->
            val adapter = TimeTableAdapter(lessons, viewModel.teacher, viewModel.toDoList)
            view.lessonsList.adapter = adapter
            view.lessonsList.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        }

        viewModel.updateScheduleList()
    }

    override fun onResume() {
        super.onResume()
        selectGroupReceiver = SelectGroupReceiver(this)
        context?.registerReceiver(selectGroupReceiver, IntentFilter().apply {
            addAction(GROUP_SELECTED)
        })
    }

    override fun onStop() {
        super.onStop()
        context?.unregisterReceiver(selectGroupReceiver)
    }

    override fun onGroupSelected(id: Long) {
        viewModel.setSelectedGroup(id)
    }
}