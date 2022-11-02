package com.example.projectmaximummodule.data.network.retorfit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatBodyResponse(
    @SerialName("created_at")
    val createdAt: Long,
    val id: Long,
    @SerialName("is_receive_response")
    val isReceiveResponse: Boolean,
    val title: String? = null,
    @SerialName("updated_at")
    val updateAt: String,
    val messages: List<Message>
) {
    @Serializable
    data class Message(
        val body: String,
        @SerialName("conversation_id")
        val conversationId: Long,
        @SerialName("created_at")
        val createdAt: Long,
        val id: Long,
        val sender: InterlocutorResponse,
        @SerialName("updated_at")
        val updateAt: String
    ) {
        var isMessageOutPut: Boolean = false
    }
}