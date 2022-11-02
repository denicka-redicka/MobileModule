package com.example.projectmaximummodule.data.network.retorfit.response

import kotlinx.serialization.Serializable

@Serializable
data class InterlocutorResponse(
    val avatar: String?,
    val firstName: String,
    val lastName: String,
    val id: Long
) {
    fun getFullName() = "$firstName $lastName"

}