package com.example.projectmaximummodule.ui.sсhedule.view

import android.annotation.SuppressLint
import android.app.Dialog
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.clear
import coil.load
import coil.transform.CircleCropTransformation
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.data.network.retorfit.response.*
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.dialog_statistics.*
import kotlinx.android.synthetic.main.holder_course_info.view.*
import kotlinx.android.synthetic.main.holder_course_info.view.progressBar
import kotlinx.android.synthetic.main.holder_lesson.view.*
import kotlinx.android.synthetic.main.holder_todo.view.*
import kotlin.IllegalArgumentException

class TimeTableAdapter(
    private val lessons: List<LessonsResponse.LessonResponse>,
    private val teacher: TeacherResponse,
    private val statistics: GroupStatisticsResponse,
    private val toDoList: List<ToDoResponse>?,
    private val onToDoClickListener: OnToDoClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    interface OnToDoClickListener {

        fun onTheoryItemClicked(id: Int)
        fun onDebtsItemClicked()
    }

    private companion object {

        const val WEBINAR = "webinar"
        const val OFFLINE = "offline"
        const val STOP = "STOP"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.holder_course_info -> CourseInformationViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.holder_course_info, parent, false)
            )

            R.layout.holder_todo -> ToDoListViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.holder_todo, parent, false),
                onToDoClickListener
            )

            R.layout.holder_lesson -> LessonViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.holder_lesson, parent, false)
            )

            else -> throw IllegalArgumentException("Illegal type: $viewType")

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CourseInformationViewHolder -> holder.bind(teacher, statistics)
            is ToDoListViewHolder -> holder.bind(toDoList)
            is LessonViewHolder -> holder.bind(lessons[position - 2])
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

        private val header: TextView = view.lessonHeader
        private val address: TextView = view.addressLine
        private val date: TextView = view.dateText
        private val timing: TextView = view.timingText
        private val location: TextView = view.locationText
        private val state: TextView = view.lessonState
        private val progress: TextView = view.progressText
        private val progressBar: ProgressBar = view.progressBar
        private val topicsList: RecyclerView = view.topicList
        private val context = view.context

        private companion object {
            const val FAILURE_STATUS = "failure"
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(lesson: LessonsResponse.LessonResponse) {
            header.text = lesson.title
            address.text = when {
                lesson.lessonType == WEBINAR -> ""
                lesson.lessonType == OFFLINE && lesson.place.subway.isNotEmpty() ->
                    context?.getString(
                        R.string.address_line_with_subway,
                        lesson.place.subway,
                        lesson.place.address
                    )
                lesson.lessonType == OFFLINE && lesson.place.subway.isEmpty() ->
                    context?.getString(R.string.address_line, lesson.place.address)
                else -> ""
            }
            val adapter = TopicAdapter(lesson.subjects ?: listOf())
            topicsList.adapter = adapter
            topicsList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

            date.text = lesson.getDateString()
            timing.text = lesson.getTiming()
            progress.text = context?.getString(R.string.text_progress, lesson.progress)
            progressBar.progress = lesson.progress
            if (lesson.presenceStatus == FAILURE_STATUS) {
                state.text = context?.getString(R.string.not_visit)
                state.background = context?.getDrawable(R.drawable.state_not_visit_rect)
            } else {
                state.text = context?.getString(R.string.visit)
                state.background = context?.getDrawable(R.drawable.state_visit_rect)
            }

            location.text = if (lesson.lessonType == WEBINAR) {
                context?.getString(R.string.webinar)
            } else context?.getString(R.string.offline)
        }

    }

    private class CourseInformationViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val teacherName: TextView = view.teacherName
        private val teacherImg: ImageView = view.teacherAvatar
        private val scorePoint: TextView = view.scoreCount
        private val progressBar: ProgressBar = view.progressBar
        private val progressPercent: TextView = view.percentText
        private val moreDetailsButton: MaterialButton = view.detailsButton
        private val context = view.context

        @SuppressLint("SetTextI18n")
        fun bind(teacher: TeacherResponse, statistics: GroupStatisticsResponse) {
            teacherName.text = teacher.fullName
            val url = teacher.userpick ?: ""
            if (url != "null") {
                teacherImg.load(url) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
            } else {
                teacherImg.clear()
            }

            scorePoint.text = ""
            progressBar.progress = statistics.progress ?: 0
            progressPercent.text = "${statistics.progress ?: 0}%"

            moreDetailsButton.setOnClickListener {
                showDialog(statistics)
            }
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        private fun showDialog(statistics: GroupStatisticsResponse) {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.dialog_statistics)
            dialog.window?.setBackgroundDrawable(context.getDrawable(R.drawable.super_rect))

            val progressHeader = dialog.progressHeader
            val progressBar = dialog.progressBar
            val exerciseCount = dialog.exercisesCount
            val videoCount = dialog.videoCount
            val lessonsCount = dialog.lessonsCount
            val theoryCount = dialog.theoryCount
            val doneButton = dialog.doneButton

            progressHeader.text = context.getString(R.string.your_progress, statistics.progress)
            progressBar.progress = statistics.progress ?: 0

            exerciseCount.text = context.getString(
                R.string.items_count,
                statistics.attemptEducationTest ?: 0, statistics.educationTest ?: 0
            )
            theoryCount.text = context.getString(
                R.string.items_count,
                statistics.attemptKnowledgeBaseSection ?: 0,
                statistics.knowledgeBaseSection ?: 0
            )
            videoCount.text = context.getString(
                R.string.items_count,
                statistics.attemptEducationVideo ?: 0, statistics.educationVideo ?: 0
            )
            lessonsCount.text = context.getString(
                R.string.items_count,
                statistics.present ?: 0, statistics.lessonOffline ?: 0
            )

            doneButton.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }
    }

    private class ToDoListViewHolder(view: View, val onToDoClickListener: OnToDoClickListener) :
        RecyclerView.ViewHolder(view) {

        private val repeat: LinearLayoutCompat = view.repeatLayout
        private val prepare: LinearLayoutCompat = view.prepareLayout
        private val connect: LinearLayoutCompat = view.connectLayout
        private val correct: LinearLayoutCompat = view.correctLayout
        private val practice: LinearLayoutCompat = view.practiceLayout
        private val context = view.context

        fun bind(toDoList: List<ToDoResponse>?) {
            toDoList?.forEach { toDoItem ->
                when (toDoItem.type) {
                    "repeat" -> {
                        repeat.visibility = View.VISIBLE
                        repeat.repeatLessonNumber.text = toDoItem.title.toString()
                        repeat.repeatCount.text = context?.getString(
                            R.string.done_exercise,
                            toDoItem.getStatsTryCount(), toDoItem.getStatsAllCount()
                        )
                    }
                    "prepare" -> {
                        prepare.visibility = View.VISIBLE
                        prepare.prepareLessonNumber.text = toDoItem.title.toString()
                        prepare.prepareCount.text = context?.getString(
                            R.string.done_theory,
                            toDoItem.getStatsCompletedCount(), toDoItem.getStatsAllCount()
                        )
                        prepare.setOnClickListener {
                            onToDoClickListener.onTheoryItemClicked(-1)
                        }
                    }
                    "connect" -> {
                        connect.visibility = View.VISIBLE
                        connect.connectLessonNumber.text = toDoItem.title.toString()
                        connect.connectDate.text = toDoItem.getTimeStamp()
                        connect.setOnClickListener {
                            if (toDoItem.connectLink != null)
                                context?.startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        toDoItem.connectLink.toUri()
                                    )
                                )
                            else Toast.makeText(
                                context,
                                "Something wrong with Lesson's link",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    "correction" -> {
                        if ((toDoItem.testsCount ?: 0) > 0) {
                            correct.visibility = View.VISIBLE
                            correct.correctCount.text = context?.getString(
                                R.string.corrected_mistakes,
                                toDoItem.testsRightSolvedCount ?: 0, toDoItem.testsCount ?: 0
                            )
                        }

                    }
                    "debts" -> {
                        practice.visibility = View.VISIBLE
                        practice.resolveCount.text =
                            context?.getString(R.string.lefted_exercise, toDoItem.getResolveCount())
                        practice.setOnClickListener {
                            onToDoClickListener.onDebtsItemClicked()
                        }
                    }
                }
            }
        }
    }
}