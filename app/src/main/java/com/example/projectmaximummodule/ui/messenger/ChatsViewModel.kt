package com.example.projectmaximummodule.ui.messenger

import androidx.core.text.HtmlCompat
import androidx.core.text.toSpanned
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.projectmaximummodule.application.BaseViewModel
import com.example.projectmaximummodule.data.network.retorfit.MainApiService
import com.example.projectmaximummodule.data.network.retorfit.request.MessageRequest
import com.example.projectmaximummodule.data.network.retorfit.response.ChatBodyResponse
import com.example.projectmaximummodule.data.network.retorfit.response.ChatInfoResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val api: MainApiService
): BaseViewModel() {

    companion object {
        const val CHAT_ID = "chat_id"
    }

    private val mutableChatsLiveData = MutableLiveData<List<ChatInfoResponse>>()
    val chatsLiveData: LiveData<List<ChatInfoResponse>> = mutableChatsLiveData

    private val mutableMessagesLiveData = MutableLiveData<ChatBodyResponse>()
    val chatsMessagesLiveData: LiveData<ChatBodyResponse> = mutableMessagesLiveData

    var chatId = -1L

    fun getChatsList() {
        coroutineScope.launch {
            mutableChatsLiveData.postValue(api.getAllChats())
        }
    }

    fun getMessages() {
        coroutineScope.launch {
            //TODO remove next line and move it to activityViewModel
            val me = api.getInfoAboutMe()
            val chatBody = api.getChat(chatId)
            chatBody.messages.forEach { message ->
                message.isMessageOutPut = message.sender.id == me.id
            }
            mutableMessagesLiveData.postValue(chatBody)
        }
    }

    fun sendMessage(messageText: String) {
        coroutineScope.launch {
            val messageToHtml = HtmlCompat.toHtml(messageText.toSpanned(), HtmlCompat.TO_HTML_PARAGRAPH_LINES_INDIVIDUAL)
            api.sendMessage(MessageRequest(messageToHtml), chatId)
            //TODO change updating messages list
            getMessages()
        }
    }
}