package com.example.projectmaximummodule.data.network.retorfit.response

import kotlinx.serialization.Serializable

@Serializable
data class StatisticsResponse (
    val practice: StatsPracticeResponse,
    val theory: StatsTheoryResponse,
    val video: StatsVideoResponse
    )