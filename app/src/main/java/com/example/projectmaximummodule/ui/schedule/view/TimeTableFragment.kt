package com.example.projectmaximummodule.ui.schedule.view

import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.core.application.SelectGroupReceiver
import com.example.projectmaximummodule.core.application.SelectGroupReceiver.Companion.GROUP_SELECTED
import com.example.projectmaximummodule.core.navigation.NavigationDestination
import com.example.projectmaximummodule.ui.schedule.TimeTableViewModel
import com.example.projectmaximummodule.util.showSystemMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_shedule.view.*

@AndroidEntryPoint
class TimeTableFragment: Fragment(R.layout.fragment_shedule), SelectGroupReceiver.OnGroupSelectedListener,
    TimeTableAdapter.ClickListener {

    private val viewModel: TimeTableViewModel by viewModels()
    private lateinit var selectGroupReceiver: SelectGroupReceiver
    private var navController: NavController? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        viewModel.lessonsLiveData.observe(viewLifecycleOwner) { lessons ->
            val adapter = TimeTableAdapter(
                lessons.items,
                viewModel.teacher,
                viewModel.statistics,
                viewModel.toDoList,
                this
            )
            view.lessonsList.adapter = adapter
            view.lessonsList.layoutManager =
                LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
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

    override fun onPause() {
        super.onPause()
        context?.unregisterReceiver(selectGroupReceiver)
    }

    override fun onGroupSelected(id: Long) {
        viewModel.setSelectedGroup(id)
    }

    override fun onTheoryItemClicked(lessonId: Long) {
    }

    override fun onDebtsItemClicked() {
        viewModel.navigate(NavigationDestination(
            graphId = R.id.homeworkNavigation
        ))
    }

    override fun onPracticeClicked(curriculumSubjectId: Long, lessonId: Long) {
        if (curriculumSubjectId != -1L || lessonId != -1L) {
            navController?.navigate(Uri.parse("https://education.maximumtest.ru/lesson/$lessonId/subjects/$curriculumSubjectId/practice/kbs/"))
        } else {
            showSystemMessage("Something goes wrong..")
        }
    }
}