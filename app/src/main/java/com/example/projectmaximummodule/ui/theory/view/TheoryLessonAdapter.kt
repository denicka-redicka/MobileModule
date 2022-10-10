package com.example.projectmaximummodule.ui.theory.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.data.network.retorfit.response.LessonsResponse
import com.example.projectmaximummodule.data.network.retorfit.response.TheoryResponse
import kotlinx.android.synthetic.main.holder_theory_lesson.view.*

class TheoryLessonAdapter(
    private val lessonsList: List<LessonsResponse.LessonResponse>,
    private val onTopicItemListener: (topicId: Int) -> Unit
) : RecyclerView.Adapter<TheoryLessonAdapter.TheoryLessonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheoryLessonViewHolder {
        return  TheoryLessonViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.holder_theory_lesson, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TheoryLessonViewHolder, position: Int) {
        val lesson = lessonsList[position]
        holder.bind(lesson)
    }

    override fun getItemCount(): Int {
        return lessonsList.size
    }

    override fun getItemId(position: Int): Long = lessonsList[position].id

    inner class TheoryLessonViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val lessonHeader = view.theoryLessonHeader
        private val timeStamp = view.timeStamp
        private val topicsList = view.theoryTopicsList
        private val adapter = TheoryTopicsAdapter(onTopicItemListener)

        fun bind(lesson: LessonsResponse.LessonResponse) {
            lessonHeader.text = lesson.title
            timeStamp.text = "${lesson.getDateString()} ${lesson.getTiming()}"

            adapter.mainList = lesson.lessonTopicsList
            topicsList.adapter = adapter
            topicsList.layoutManager =
                LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<TheoryResponse>() {
        override fun areItemsTheSame(oldItem: TheoryResponse, newItem: TheoryResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TheoryResponse, newItem: TheoryResponse): Boolean {
            return oldItem == newItem
        }
    }
}