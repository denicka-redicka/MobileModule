package com.example.projectmaximummodule.data.network.retorfit.response

import kotlinx.serialization.Serializable

@Serializable
data class StatisticsResponse (
    val practice: PracticeResponse,
    val theory: TheoryResponse,
    val video: VideoResponse

        )