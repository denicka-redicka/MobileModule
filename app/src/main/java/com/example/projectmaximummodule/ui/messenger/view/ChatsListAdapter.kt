package com.example.projectmaximummodule.ui.messenger.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import coil.transform.CircleCropTransformation
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.data.network.retorfit.response.ChatInfoResponse
import kotlinx.android.synthetic.main.holder_chat_info.view.*

class ChatsListAdapter(private val onDialogClickListener: OnDialogClickListener): ListAdapter<ChatInfoResponse, ChatsListAdapter.DialogViewHolder>(DialogDiffCallback()) {

    interface OnDialogClickListener {
        fun onDialogClicked(dialogId: Long, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogViewHolder {
        return DialogViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.holder_chat_info, parent, false))
    }

    override fun onBindViewHolder(holder: DialogViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DialogViewHolder(view: View): ViewHolder(view) {

        private val avatar = view.chatImage
        private val fullName = view.chatHeader
        private val chatTitle = view.chatTitle
        private val updatedAt = view.updatedAt
        private var chat: ChatInfoResponse? = null

        init {
            view.setOnClickListener {
                    onDialogClickListener.onDialogClicked(requireNotNull(chat).id, layoutPosition)
            }
        }
        fun bind(chat: ChatInfoResponse) {
            this.chat = chat

            avatar.load(chat.interlocutor.avatar) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            fullName.text = chat.interlocutor.getFullName()
            chatTitle.text = chat.title
            updatedAt.text = chat.updateAt
        }
    }

    class DialogDiffCallback : DiffUtil.ItemCallback<ChatInfoResponse>() {
        override fun areItemsTheSame(oldItem: ChatInfoResponse, newItem: ChatInfoResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ChatInfoResponse, newItem: ChatInfoResponse): Boolean {
            return oldItem == newItem
        }
    }
}