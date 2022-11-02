package com.example.projectmaximummodule.ui.messenger.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.ui.messenger.ChatsViewModel
import com.example.projectmaximummodule.ui.messenger.ChatsViewModel.Companion.CHAT_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_chats.*

@AndroidEntryPoint
class ChatsFragment : Fragment(R.layout.fragment_chats), ChatsListAdapter.OnDialogClickListener {

    private val viewModel: ChatsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ChatsListAdapter(this)

        viewModel.chatsLiveData.observe(viewLifecycleOwner) { dialogsList ->
            if (dialogsList.isNotEmpty()) {
                adapter.submitList(dialogsList)
            }
            else {
                chatsList.visibility = View.GONE
                emptyChatsListMessage.visibility = View.VISIBLE
            }
        }

        chatsList.adapter = adapter
        chatsList.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)

        if (savedInstanceState == null) {
            viewModel.getChatsList()
        }
    }

    override fun onDialogClicked(dialogId: Long, position: Int) {
        val bundle = Bundle().also {
            it.putString(CHAT_ID, dialogId.toString())
        }
        findNavController().navigate(R.id.action_chatsFragment_to_chatItemFragment, bundle)
    }
}