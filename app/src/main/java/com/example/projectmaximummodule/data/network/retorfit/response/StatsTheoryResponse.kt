package com.example.projectmaximummodule.data.network.retorfit.response

import kotlinx.serialization.Serializable

@Serializable
data class StatsTheoryResponse (
    val all: Int,
    val completed: Int
)
