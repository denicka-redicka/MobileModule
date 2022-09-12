package com.example.projectmaximummodule.data.network.retorfit.response

import kotlinx.serialization.Serializable

@Serializable
data class SubjectResponse (
    val id: Long,
    val title: String,
    val knowledgeId: Long,
    val statistics: StatisticsResponse
    )