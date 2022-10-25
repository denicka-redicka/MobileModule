package com.example.projectmaximummodule.data.network.retorfit.response

import kotlinx.serialization.Serializable

@Serializable
data class TestResponse(
    val id: Int,
    val attemptsCountLeft: Int? = null,
    val maxPoints: Int,
    val question: String,
    val solution: String? = null,
    val educationTestFileId: Int,
    val educationTestAnswers: List<EducationTestAnswerResponse>,
    val type: String,
    var studentTestResult: AnswerResultResponse?
    )