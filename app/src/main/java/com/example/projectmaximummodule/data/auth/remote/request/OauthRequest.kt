package com.example.projectmaximummodule.data.auth.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class OauthRequest(
    val code: String,
    val session: String,
    val state: String,
    val fromUrl: String
    )
