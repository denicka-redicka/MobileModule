package com.example.projectmaximummodule.data.network.retorfit.response

import kotlinx.serialization.Serializable

@Serializable
data class CurriculumSubjectsResponse(
    val id: Long,
    val educationTestId: Long,
    val knowledgeBaseSectionId: Long,
    val title: String,
    val allDebts: Int,
    val paidDebts: Int,
) {

    fun convertToDebtsItem(lessonIdFromParent: Long) = DebtsResponse.DebtsItems(
        lessonId = lessonIdFromParent,
        curriculumSubjectId = this.id,
        title = this.title,
        startAt = null,
        knowledgeBaseSectionId = this.knowledgeBaseSectionId,
        debtsCount = this.allDebts - this.paidDebts,
        isOpen = false,
        curriculumSubjects = null
    )
}