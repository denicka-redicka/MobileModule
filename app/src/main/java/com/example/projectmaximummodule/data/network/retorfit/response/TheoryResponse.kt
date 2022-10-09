package com.example.projectmaximummodule.data.network.retorfit.response

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class TheoryResponse(
    val id: Int,
    val title: String,
    val kbs: List<TheoryKbsResponse>?,
    val knowledgeId: Int?,
) {

    @kotlinx.serialization.Serializable
    data class TheoryKbsResponse(
        val id: Int,
        val title: String,
        val read: Boolean,
        val children: List<TheoryKbsResponse>? = null,
        @SerialName("curriculum_subject_id")
        val curriculumSubjectId: Int?,
        @SerialName("has_content")
        val hasContent: Boolean,
        @SerialName("is_root")
        val isRoot: Boolean? = null,
        @SerialName("parent_id")
        val parentId: Int?,
    )
}