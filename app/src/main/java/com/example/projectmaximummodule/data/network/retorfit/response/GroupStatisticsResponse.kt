package com.example.projectmaximummodule.data.network.retorfit.response

import kotlinx.serialization.Serializable

@Serializable
data class GroupStatisticsResponse (
    val attemptEducationVideo : Int?,
    val attemptKnowledgeBaseSection : Int?,
    val educationTest : Int?,
    val educationVideo : Int?,
    val knowledgeBaseSection : Int?,
    val lessonOffline : Int?,
    val present : Int?,
    val progress : Int?,
    val attemptEducationTest : Int?
) {
    var summary = 0
}