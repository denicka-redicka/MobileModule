package com.example.projectmaximummodule.data.messenger

import com.example.projectmaximummodule.data.messenger.remote.MessengerRemoteDataSource
import com.example.projectmaximummodule.data.network.retorfit.request.MessageRequest
import com.example.projectmaximummodule.data.network.retorfit.response.ChatBodyResponse
import com.example.projectmaximummodule.data.network.retorfit.response.ChatInfoResponse
import com.example.projectmaximummodule.util.RemoteResult
import kotlinx.serialization.Serializable
import javax.inject.Inject

class MessengerRepositoryImpl @Inject constructor(
    private val remoteDataSource: MessengerRemoteDataSource
) : MessengerRepository {

    override suspend fun getChatsList(): RemoteResult<List<ChatInfoResponse>, Throwable> {
        return remoteDataSource.getChatsList()
    }

    override suspend fun getMessages(chatId: Long): RemoteResult<ChatBodyResponse, Throwable> {
        return remoteDataSource.getMessages(chatId)
    }

    override suspend fun sendMessage(message: MessageRequest, chatId: Long) {
        remoteDataSource.sendMessage(message, chatId)
    }

    override suspend fun getUnreadCount(): RemoteResult<@Serializable Int, Throwable> {
        return remoteDataSource.getUnreadCount()
    }

    override suspend fun setViewed(chatId: Long) {
        remoteDataSource.setViewed(chatId)
    }
}