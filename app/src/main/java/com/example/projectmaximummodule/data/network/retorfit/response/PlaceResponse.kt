package com.example.projectmaximummodule.data.network.retorfit.response

import kotlinx.serialization.Serializable

@Serializable
data class PlaceResponse(
    val address: String,
    val city: String,
    val id: Long,
    val subway: String,
    val title: String,
    val location: LocationResponse
)