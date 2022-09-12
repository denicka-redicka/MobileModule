package com.example.projectmaximummodule.data.network.retorfit.response

import kotlinx.serialization.Serializable

@Serializable
data class GroupResponse (
    val id: Long,
    val title: String,
    val educationCourse: EducationCourseResponse
)

