package com.example.projectmaximummodule.ui.shedule.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.clear
import coil.load
import coil.transform.CircleCropTransformation
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.data.network.retorfit.response.LessonResponse
import com.example.projectmaximummodule.data.network.retorfit.response.TeacherResponse
import com.example.projectmaximummodule.data.network.retorfit.response.ToDoResponse
import kotlinx.android.synthetic.main.holder_course_info.view.*
import kotlinx.android.synthetic.main.holder_todo.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.IllegalArgumentException

class TimeTableAdapter(
    private val context: Context?,
    private val lessons: List<LessonResponse>,
    private val teacher: TeacherResponse,
    private val toDoList: List<ToDoResponse>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private companion object {

        @SuppressLint("SimpleDateFormat")
        fun getTimeStamp(date: Long?): String {
            val sdf = SimpleDateFormat("dd.MM.yyyy HH.mm")
            return if (date != null) {
                val netDate = Date(date * 1000)
                sdf.format(netDate)
            } else {
                ""
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.holder_course_info -> CourseInformationViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.holder_course_info, parent, false))

            R.layout.holder_todo -> ToDoListViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.holder_todo, parent, false), context)

            R.layout.holder_lesson -> LessonViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.holder_lesson, parent, false))

            else -> throw IllegalArgumentException("Illegal type: $viewType")

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CourseInformationViewHolder -> holder.bind(teacher)
            is ToDoListViewHolder -> holder.bind(toDoList)
            is LessonViewHolder -> holder.bind()

        }
    }

    override fun getItemCount(): Int {
        return lessons.size + 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> R.layout.holder_course_info
            1 -> R.layout.holder_todo
            else -> R.layout.holder_lesson
        }
    }

    private class LessonViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind() {
        }

    }

    private class CourseInformationViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val teacherName: TextView = view.teacherName
        private val teacherImg: ImageView = view.teacherAvatar
        private val scorePoint: TextView = view.scoreCount
        private val progressBar: ProgressBar = view.progressBar
        private val progressPercent: TextView = view.percentText

        fun bind(teacher: TeacherResponse) {
            teacherName.text = teacher.fullName
            val url = teacher.userpick?: ""
            if (url != "null") {
                teacherImg.load(url) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
            } else {
                teacherImg.clear()
            }

            scorePoint.text = "340"
            progressBar.progress = 60
            progressPercent.text = "60%"
        }

    }

    private class ToDoListViewHolder(view: View, val context: Context?): RecyclerView.ViewHolder(view) {

        private val pin: LinearLayoutCompat = view.repeatLayout
        private val connect: LinearLayoutCompat = view.connectLayout
        private val correct: LinearLayoutCompat = view.correctLayout
        private val practice: LinearLayoutCompat = view.practiceLayout

        fun bind(toDoList: List<ToDoResponse>?) {
            toDoList?.forEach { toDoItem ->
                when (toDoItem.type) {
                    "repeat" -> {
                        pin.visibility = View.VISIBLE
                        pin.consolidateLessonNumber.text = toDoItem.title.toString()
                        pin.consolidateCount.text = context?.getString(R.string.done_exercise,
                            toDoItem.getStatsTryPracticeCount(), toDoItem.getStatsAllPracticecont())
                    }
                    "connect" -> {
                        connect.visibility = View.VISIBLE
                        connect.connectLessonNumber.text = toDoItem.title.toString()
                        connect.connectDate.text = getTimeStamp(toDoItem.dateStart)
                        connect.setOnClickListener{
                            if (toDoItem.connectLink != null)
                                context?.startActivity(Intent(Intent.ACTION_VIEW, toDoItem.connectLink.toUri()))
                            else Toast.makeText(context, "Something wrong with Lesson's link", Toast.LENGTH_SHORT).show()
                        }
                    }
                    "correction" -> {
                        if ((toDoItem.testsCount ?: 0) > 0) {
                            correct.visibility =  View.VISIBLE
                            correct.correctCount.text = context?.getString(R.string.corrected_mistakes,
                                toDoItem.testsRightSolvedCount?: 0, toDoItem.testsCount?: 0)
                        }

                    }
                    "debts" -> {
                        practice.visibility = View.VISIBLE
                        practice.resolveCount.text = context?.getString(R.string.lefted_exercise, toDoItem.getResolveCount())
                    }
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<LessonResponse>() {
        override fun areItemsTheSame(oldItem: LessonResponse, newItem: LessonResponse): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: LessonResponse, newItem: LessonResponse): Boolean {
            return oldItem == newItem
        }
    }
}