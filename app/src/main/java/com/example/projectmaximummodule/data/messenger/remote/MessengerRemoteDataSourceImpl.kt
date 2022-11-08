package com.example.projectmaximummodule.data.messenger.remote

import com.example.projectmaximummodule.data.network.retorfit.MainApiService
import com.example.projectmaximummodule.data.network.retorfit.request.MessageRequest
import com.example.projectmaximummodule.util.runResultCatching
import javax.inject.Inject

class MessengerRemoteDataSourceImpl @Inject constructor(
    private val api: MainApiService
): MessengerRemoteDataSource {

    override suspend fun getChatsList() = runResultCatching {
        api.getAllChats()
    }

    override suspend fun getMessages(chatId: Long) = runResultCatching {
        val me = api.getInfoAboutMe()
        val chatBody = api.getChat(chatId)
        chatBody.messages.forEach { message ->
            message.isMessageOutPut = message.sender.id == me.id
        }
        return@runResultCatching chatBody
    }

    override suspend fun sendMessage(message: MessageRequest, chatId: Long) = runResultCatching {
        api.sendMessage(message, chatId)
    }

    override suspend fun getUnreadCount() = runResultCatching {
        api.getUnreadCount()
    }

    override suspend fun setViewed(chatId: Long) = runResultCatching {
        api.setChatViewed(chatId)
    }
}