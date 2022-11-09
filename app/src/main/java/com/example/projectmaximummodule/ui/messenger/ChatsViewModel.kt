package com.example.projectmaximummodule.ui.messenger

import androidx.core.text.HtmlCompat
import androidx.core.text.toSpanned
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.projectmaximummodule.application.BaseViewModel
import com.example.projectmaximummodule.data.messenger.MessengerRepository
import com.example.projectmaximummodule.data.network.retorfit.request.MessageRequest
import com.example.projectmaximummodule.data.network.retorfit.response.ChatBodyResponse
import com.example.projectmaximummodule.data.network.retorfit.response.ChatInfoResponse
import com.example.projectmaximummodule.util.RemoteResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val repository: MessengerRepository
): BaseViewModel() {

    companion object {
        const val CHAT_ID = "chat_id"
    }

    private val mutableChatsLiveData = MutableLiveData<RemoteResult<List<ChatInfoResponse>, Throwable>>()
    val chatsLiveData: LiveData<RemoteResult<List<ChatInfoResponse>, Throwable>> = mutableChatsLiveData

    private val mutableMessagesLiveData = MutableLiveData<RemoteResult<ChatBodyResponse, Throwable>>()
    val chatsMessagesLiveData: LiveData<RemoteResult<ChatBodyResponse, Throwable>> = mutableMessagesLiveData

    var chatId = -1L

    fun getChatsList() {
        coroutineScope.launch {
            mutableChatsLiveData.postValue(repository.getChatsList())
        }
    }

    fun getMessages() {
        coroutineScope.launch {
            mutableMessagesLiveData.postValue(repository.getMessages(chatId))
        }
    }

    fun sendMessage(messageText: String) {
        coroutineScope.launch {
            val messageToHtml = HtmlCompat.toHtml(messageText.toSpanned(), HtmlCompat.TO_HTML_PARAGRAPH_LINES_INDIVIDUAL)
            repository.sendMessage(MessageRequest(messageToHtml), chatId)
            //TODO change updating messages list
            getMessages()
        }
    }
}