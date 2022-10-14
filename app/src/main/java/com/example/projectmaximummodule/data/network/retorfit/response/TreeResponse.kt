package com.example.projectmaximummodule.data.network.retorfit.response

import kotlinx.serialization.Serializable

@Serializable
data class TreeResponse(
    val aftertheory: List<KnowledgeBaseInfo>,
    val offline: List<KnowledgeBaseInfo>,
    val online: List<KnowledgeBaseInfo>
) {
    @Serializable
    data class KnowledgeBaseInfo(
        val id: Int,
        val title: String,
    )
}