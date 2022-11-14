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
    ) {

    companion object {
        const val ANSWERS_FIRST_TYPE = "type1"
        const val ANSWERS_SECOND_TYPE = "type2"
        const val ANSWERS_THIRD_TYPE = "type1"
        const val ANSWERS_FOURTH_TYPE = "type2"
        const val ANSWERS_FIFTH_TYPE = "type5"
        const val ANSWERS_SIXTH_TYPE = "type6"

    }
}