package com.example.projectmaximummodule.data.network.retorfit.response

import kotlinx.serialization.Serializable

@Serializable
data class LessonResponse (
    val dateStart: Long,
    val duration: Long,
    val isActual: Boolean,
    val lessonType: String,
    val place: PlaceResponse,
    val presenceStatus: String,
    val subject: List<SubjectResponse>,
    val title: String,
)