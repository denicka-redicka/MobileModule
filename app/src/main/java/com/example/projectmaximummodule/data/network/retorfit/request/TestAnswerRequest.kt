package com.example.projectmaximummodule.data.network.retorfit.request

import kotlinx.serialization.Serializable

@Serializable
data class TestAnswerRequest (
    val answer: List<String>,
    val lessonId: Long,
    val spentTime: Int,
)