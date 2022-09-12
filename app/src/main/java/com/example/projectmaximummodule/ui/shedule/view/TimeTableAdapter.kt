package com.example.projectmaximummodule.ui.shedule.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
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
import kotlin.IllegalArgumentException

class TimeTableAdapter(
    val lessons: List<LessonResponse>,
    val teacher: TeacherResponse,
    val toDoList: List<ToDoResponse>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.holder_course_info -> CourseInformationViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.holder_course_info, parent, false))

            R.layout.holder_todo -> ToDoListViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.holder_todo, parent, false))

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
            if (!url.equals("null") ) {
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

    private class ToDoListViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val pin: LinearLayoutCompat = view.repeatLayout
        private val connect: LinearLayoutCompat = view.connectLayout
        private val correct: LinearLayoutCompat = view.correctLayout
        private val practice: LinearLayoutCompat = view.practiceLayout

        fun bind(toDoList: List<ToDoResponse>?) {
            toDoList?.forEach { toDoItem ->
                when (toDoItem.type) {
                    "repeat" -> {
                        pin.visibility = View.VISIBLE
                    }
                    "connect" -> {
                        connect.visibility = View.VISIBLE
                    }
                    "correction" -> {
                        correct.visibility = View.VISIBLE
                    }
                    "debts" -> {
                        practice.visibility = View.VISIBLE
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