package com.example.projectmaximummodule.data.network.retorfit.response

import kotlinx.serialization.Serializable

@Serializable
data class DebtsLessonResponse(
    val id: Long,
    val title: String,
    val startAt: Long,
    val allDebts: Int,
    val paidDebts: Int,
    val curriculumSubjects: Map<Long, CurriculumSubjectsResponse>
)