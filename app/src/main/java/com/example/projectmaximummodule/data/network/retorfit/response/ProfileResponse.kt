package com.example.projectmaximummodule.data.network.retorfit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    @SerialName("email")val email: String?,
    @SerialName("id")val id: Long,
    @SerialName("first_name")val firstName: String?,
    @SerialName("last_name")val lastName: String?,
    @SerialName("role")val role: String,
    @SerialName("phone")val phone: String?,
    @SerialName("userpick")val avatar: String?,
    @SerialName("birth_date")val birtDate: Long?
    )