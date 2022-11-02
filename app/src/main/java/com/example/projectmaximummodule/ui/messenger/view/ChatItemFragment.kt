package com.example.projectmaximummodule.ui.messenger.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.ui.messenger.ChatsViewModel
import com.example.projectmaximummodule.ui.messenger.ChatsViewModel.Companion.CHAT_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_chat_item.*

@AndroidEntryPoint
class ChatItemFragment: Fragment(R.layout.fragment_chat_item) {

    private val viewModel: ChatsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.chatId = arguments?.getString(CHAT_ID)?.toLong()?: -1L
        val adapter = MessagesAdapter()

        sendButton.setOnClickListener {
            if (!messageEditText.text.isNullOrEmpty()) {
                viewModel.sendMessage(messageEditText.text.toString())
                messageEditText.text = null
            }
        }
        messagesList.adapter = adapter
        messagesList.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false).also {
            it.stackFromEnd = true
        }
        viewModel.chatsMessagesLiveData.observe(viewLifecycleOwner) { chatBody ->
            adapter.submitList(chatBody.messages)
        }

        if (savedInstanceState == null) {
            viewModel.getMessages()
        }
    }
}