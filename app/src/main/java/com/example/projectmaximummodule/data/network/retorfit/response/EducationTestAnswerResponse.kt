package com.example.projectmaximummodule.data.network.retorfit.response

@kotlinx.serialization.Serializable
data class EducationTestAnswerResponse(
    val educationTestId: Long,
    val id: Long,
    val isRightAnswer: Boolean,
//    val updatedAt: Long,
    val variants: List<String>
) {
    var isSelected = false
    var inputAnswer = ""
}
