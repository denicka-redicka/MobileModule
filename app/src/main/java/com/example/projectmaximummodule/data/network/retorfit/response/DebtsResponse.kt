package com.example.projectmaximummodule.data.network.retorfit.response

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.*

@Serializable
data class DebtsResponse(
    val allDebts: Int,
    val paidDebts: Int,
    val lessons: Map<Long, DebtsLessonResponse>
) {

    @Serializable
    data class DebtsItems(
        val lessonId: Long?,
        val curriculumSubjectId: Long?,
        val title: String,
        val startAt: Long?,
        val knowledgeBaseSectionId: Long?,
        val debtsCount: Int,
        var isOpen: Boolean,
        val curriculumSubjects: Map<Long, CurriculumSubjectsResponse>?
    ) {

        val lessonsDebtsItems: MutableList<DebtsItems> = mutableListOf()

        @SuppressLint("SimpleDateFormat")
        fun getTimeStamp(): String {
            return if (startAt != null) {
                val sdf = SimpleDateFormat("dd.mm.yy")
                val timeStart = Date(startAt * 1000)
                return sdf.format(timeStart)
            } else ""
        }
    }

    val debtsItems: MutableList<DebtsItems> = mutableListOf()

    fun addDebtsLessons(lesson: DebtsLessonResponse) {
        val item = DebtsItems(
            lessonId = lesson.id,
            curriculumSubjectId = null,
            title = lesson.title,
            startAt = lesson.startAt,
            knowledgeBaseSectionId = null,
            debtsCount = lesson.allDebts - lesson.paidDebts,
            isOpen = false,
            curriculumSubjects = lesson.curriculumSubjects
        )

        debtsItems.add(item)
    }
}