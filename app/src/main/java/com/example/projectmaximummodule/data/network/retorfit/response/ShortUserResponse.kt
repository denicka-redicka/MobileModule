package com.example.projectmaximummodule.data.network.retorfit.response

import kotlinx.serialization.Serializable

@Serializable
data class ShortUserResponse (
    val firstName: String,
    val lastName: String,
    val id: Long,
    val userpick: String?
    ) {

    fun getFullName() = "$firstName $lastName"
}