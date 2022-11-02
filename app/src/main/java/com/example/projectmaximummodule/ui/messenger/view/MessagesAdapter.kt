package com.example.projectmaximummodule.ui.messenger.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.data.network.retorfit.response.ChatBodyResponse
import kotlinx.android.synthetic.main.holder_input_message.view.*
import kotlinx.android.synthetic.main.holder_input_message.view.messageText
import kotlinx.android.synthetic.main.holder_input_message.view.sentAt
import kotlinx.android.synthetic.main.holder_output_message.view.*

class MessagesAdapter :
    ListAdapter<ChatBodyResponse.Message, RecyclerView.ViewHolder>(MessageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == R.layout.holder_output_message)
            OutputMessageViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.holder_output_message, parent, false))
        else
            InputMessageViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.holder_input_message, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is OutputMessageViewHolder -> holder.bind(getItem(position))
            is InputMessageViewHolder -> holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isMessageOutPut)
            R.layout.holder_output_message
        else
            R.layout.holder_input_message
    }

    private class InputMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val messageText = view.messageText
        private val senderImg = view.senderImg
        private val senderName = view.senderName
        private val sentAt = view.sentAt

        fun bind(message: ChatBodyResponse.Message) {
            messageText.text = HtmlCompat.fromHtml(message.body, HtmlCompat.FROM_HTML_MODE_COMPACT)
            senderImg.load(message.sender.avatar) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            senderName.text = message.sender.getFullName()
            sentAt.text = message.updateAt
        }
    }

    private class OutputMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val messageText = view.myMessageText
        private val sentAt = view.mySentAt

        fun bind(message: ChatBodyResponse.Message) {
            messageText.text = HtmlCompat.fromHtml(message.body, HtmlCompat.FROM_HTML_MODE_COMPACT)
            sentAt.text = message.updateAt
        }
    }


    class MessageDiffCallback : DiffUtil.ItemCallback<ChatBodyResponse.Message>() {
        override fun areItemsTheSame(
            oldItem: ChatBodyResponse.Message,
            newItem: ChatBodyResponse.Message
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ChatBodyResponse.Message,
            newItem: ChatBodyResponse.Message
        ): Boolean {
            return oldItem == newItem
        }
    }
}