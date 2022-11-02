package com.example.projectmaximummodule.data.network.retorfit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatInfoResponse (
    @SerialName("created_at")
    val createAt: Long,
    @SerialName("has_unread")
    val hasUnread: Boolean,
    val id: Long,
    @SerialName("is_receive_response")
    val isReceiveResponse: Boolean,
    val title: String,
    @SerialName("updated_at")
    val updateAt: String,
    val interlocutor: InterlocutorResponse
    )