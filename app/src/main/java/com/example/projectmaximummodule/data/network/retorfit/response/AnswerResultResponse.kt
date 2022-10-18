package com.example.projectmaximummodule.data.network.retorfit.response

@kotlinx.serialization.Serializable
data class AnswerResultResponse(
    val addRightAnswerBeforeShowSolution: Boolean?,
    //val additional: null,
    //val attaches: List,
    //val answer: List<>,
    val attemptsCount: Int,
    val educationSubjectId: Long,
    val educationSubjectType: String,
    val educationTestId: Long,
    val endDate: Long,
    val examLessonId: Long,
    val id: Long,
    val percent: Int,
    val points: String,
    val result: String,
    val showSolution: Boolean,
    val spentTime: Int,
    val studentId: Long,
    //val teacherComment: null,
    //val teacherFeedback:
)