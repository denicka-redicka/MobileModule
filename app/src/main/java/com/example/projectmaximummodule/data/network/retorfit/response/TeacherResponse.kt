package com.example.projectmaximummodule.data.network.retorfit.response

import kotlinx.serialization.Serializable

@Serializable
data class TeacherResponse (
    val fullName: String,
    val id: Long,
    var userpick: String?,
)