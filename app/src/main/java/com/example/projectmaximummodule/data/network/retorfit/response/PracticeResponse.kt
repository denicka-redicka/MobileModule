package com.example.projectmaximummodule.data.network.retorfit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PracticeResponse (
    @SerialName("all")
    val all: Int,
    @SerialName("completed")
    val completed: Int,
    @SerialName("false")
    val falsed: Int,
    @SerialName("noAttempts")
    val noAttempts: Int
    )