package com.example.projectmaximummodule.data.network.retorfit.response

import kotlinx.serialization.Serializable

@Serializable
data class EducationCourseResponse (
    val id: Long,
    val courseDateStart: Long,
    val courseDateEnd: Long,
    val teacher: TeacherResponse,
    val title: String,
    val titleForModule: String?
)