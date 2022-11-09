package com.example.projectmaximummodule.ui.messenger.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.data.network.retorfit.response.ChatInfoResponse
import com.example.projectmaximummodule.ui.messenger.ChatsViewModel
import com.example.projectmaximummodule.ui.messenger.ChatsViewModel.Companion.CHAT_ID
import com.example.projectmaximummodule.util.RemoteResult
import com.example.projectmaximummodule.util.toGone
import com.example.projectmaximummodule.util.toVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_chats.*

@AndroidEntryPoint
class ChatsFragment : Fragment(R.layout.fragment_chats), ChatsListAdapter.OnDialogClickListener {

    private val viewModel: ChatsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ChatsListAdapter(this)

        viewModel.chatsLiveData.observe(viewLifecycleOwner) { result  ->
            when (result) {
                is RemoteResult.Success -> setupChatsListUi(adapter, result.value)
                is RemoteResult.Failed -> TODO("notice user that we have problems")
            }

        }

        chatsList.adapter = adapter
        chatsList.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)

        if (savedInstanceState == null) {
            viewModel.getChatsList()
        }
    }

    private fun setupChatsListUi(adapter: ChatsListAdapter, resultList: List<ChatInfoResponse>) {
        if (resultList.isNotEmpty()) {
            adapter.submitList(resultList)
        } else {
            chatsList.toGone()
            emptyChatsListMessage.toVisible()
        }
    }

    override fun onDialogClicked(dialogId: Long, position: Int) {
        val bundle = Bundle().also {
            it.putString(CHAT_ID, dialogId.toString())
        }
        findNavController().navigate(R.id.action_chatsFragment_to_chatItemFragment, bundle)
    }
}