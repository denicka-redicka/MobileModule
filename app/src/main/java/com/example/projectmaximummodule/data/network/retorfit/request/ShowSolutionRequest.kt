package com.example.projectmaximummodule.data.network.retorfit.request

import kotlinx.serialization.Serializable

@Serializable
data class ShowSolutionRequest (
    val lessonId: Long,
    val spentTime: Int,
    val showSolution: Boolean,
)