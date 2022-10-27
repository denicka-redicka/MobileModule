package com.example.projectmaximummodule.data.network.retorfit.response

import kotlinx.serialization.Serializable

@Serializable
data class AnswerResultResponse(
    val addRightAnswerBeforeShowSolution: Boolean?,
    val answer: List<AnswerValueResponse>,
    val attemptsCount: Int,
    val educationSubjectId: Long,
    val educationSubjectType: String,
    val educationTestId: Long,
    val endDate: Long?,
    val examLessonId: Long?,
    val id: Long,
    val percent: Int,
    val points: String,
    val result: String,
    val showSolution: Boolean?,
    val spentTime: Int,
    val studentId: Long,
    //val additional: null,
    //val attaches: List,
    //val teacherComment: null,
    //val teacherFeedback:
) {
    @Serializable
    data class AnswerValueResponse(
        val value: String?,
        //val result: Boolean  <-- it may be Boolean or String from Backend
    )
}