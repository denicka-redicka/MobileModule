package com.example.projectmaximummodule.data.network.retorfit.response

import kotlinx.serialization.Serializable

@Serializable
data class LocationResponse (
    val lat: Float,
    val lng: Float
        )