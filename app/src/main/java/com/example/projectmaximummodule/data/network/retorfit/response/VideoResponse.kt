package com.example.projectmaximummodule.data.network.retorfit.response

import kotlinx.serialization.Serializable

@Serializable
data class VideoResponse (
    val all: Int,
    val completed: Int
)
