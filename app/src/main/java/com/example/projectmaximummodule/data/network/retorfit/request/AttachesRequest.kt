package com.example.projectmaximummodule.data.network.retorfit.request

@kotlinx.serialization.Serializable
data class AttachesRequest (
    val fileName: String,
    val filePath: String
    )
