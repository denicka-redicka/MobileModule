package com.example.projectmaximummodule.data.network.retorfit.response

import kotlinx.serialization.Serializable

@Serializable
data class ShortPositionResponse (
    val position: Int,
    val summary: Long,
    val user: ShortUserResponse
    )