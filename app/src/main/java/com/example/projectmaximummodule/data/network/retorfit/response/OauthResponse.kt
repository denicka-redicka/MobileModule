package com.example.projectmaximummodule.data.network.retorfit.response

import kotlinx.serialization.Serializable

@Serializable
data class OauthResponse(
    val location: String
) {
    fun separateResponse(): Array<String> = location
        .substring(location.indexOf('?')+1)
        .split("&")
        .toTypedArray()
}