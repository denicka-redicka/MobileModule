package com.example.projectmaximummodule.data.messenger

import com.example.projectmaximummodule.data.network.retorfit.request.MessageRequest
import com.example.projectmaximummodule.data.network.retorfit.response.ChatBodyResponse
import com.example.projectmaximummodule.data.network.retorfit.response.ChatInfoResponse
import com.example.projectmaximummodule.util.RemoteResult
import kotlinx.serialization.Serializable

interface MessengerRepository {

    suspend fun getChatsList(): RemoteResult<List<ChatInfoResponse>, Throwable>

    suspend fun getMessages(chatId: Long): RemoteResult<ChatBodyResponse, Throwable>

    suspend fun sendMessage(message: MessageRequest, chatId: Long)

    suspend fun getUnreadCount(): RemoteResult<@Serializable Int, Throwable>

    suspend fun setViewed(chatId: Long)
}