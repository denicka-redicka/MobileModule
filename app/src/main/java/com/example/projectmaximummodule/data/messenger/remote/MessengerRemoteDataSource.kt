package com.example.projectmaximummodule.data.messenger.remote

import com.example.projectmaximummodule.data.network.retorfit.request.MessageRequest
import com.example.projectmaximummodule.data.network.retorfit.response.ChatBodyResponse
import com.example.projectmaximummodule.data.network.retorfit.response.ChatInfoResponse
import com.example.projectmaximummodule.util.RemoteResult
import kotlinx.serialization.Serializable

interface MessengerRemoteDataSource {

    suspend fun getChatsList(): RemoteResult<List<ChatInfoResponse>, Throwable>

    suspend fun getMessages(chatId: Long): RemoteResult<ChatBodyResponse, Throwable>

    suspend fun sendMessage(message: MessageRequest, chatId: Long): RemoteResult<Unit, Throwable>

    suspend fun getUnreadCount(): RemoteResult<@Serializable Int, Throwable>

    suspend fun setViewed(chatId: Long): RemoteResult<Unit, Throwable>
}