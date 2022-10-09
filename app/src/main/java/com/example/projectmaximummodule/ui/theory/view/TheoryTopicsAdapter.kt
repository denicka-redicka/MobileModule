package com.example.projectmaximummodule.ui.theory.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.view.MarginLayoutParamsCompat
import androidx.core.view.marginStart
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.data.TheoryItem
import com.example.projectmaximummodule.data.network.retorfit.response.TheoryResponse
import kotlinx.android.synthetic.main.holder_theory_children.view.*
import kotlinx.android.synthetic.main.holder_theory_parent.view.*

class TheoryTopicsAdapter(
    private val onTopicItemListener: (topicId: Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val HEADER_TYPE = 0
        private const val ROOT_TYPE = 1
        private const val PARENT_TYPE = 2
        private const val CHILD_TYPE = 3
    }

    private var mainList: MutableList<TheoryItem> = mutableListOf()

    fun prepareList(topicsList: List<TheoryResponse>) {
        mainList.clear()
        topicsList.forEach { theory ->
            if (theory.kbs != null) {
                mainList.add(
                    TheoryItem(
                        title = theory.title,
                        id = theory.id,
                        sublist = TheoryItem.convertFromKbs(theory.kbs),
                        isOpen = false,
                        hasContent = null,
                        isRoot = null
                    )
                )
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER_TYPE -> HeaderViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.holder_theory_parent, parent, false)
            )
            ROOT_TYPE -> RootViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.holder_theory_parent, parent, false)
            )
            PARENT_TYPE -> ParentViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.holder_theory_parent, parent, false)
            )
            CHILD_TYPE -> ChildViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.holder_theory_children, parent, false)
            )
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.bind(mainList[position], position)
            is RootViewHolder -> holder.bind(mainList[position], position)
            is ParentViewHolder -> holder.bind(mainList[position], position)
            is ChildViewHolder -> holder.bind(mainList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = mainList[position]
        return when {
            item.isRoot != null && item.isRoot -> ROOT_TYPE
            item.hasContent != null && item.hasContent -> CHILD_TYPE
            item.hasContent != null && !item.hasContent -> PARENT_TYPE
            else -> HEADER_TYPE

        }
    }

    override fun getItemCount(): Int {
        return mainList.size
    }

    fun openOrCloseView(position: Int, imageButton: ImageButton) {
        if (mainList[position].isOpen) {
            closeView(position, imageButton)
        } else {
            openView(position, imageButton)
        }
    }

    private fun openView(position: Int, imageButton: ImageButton) {
        mainList[position].sublist?.let {
            imageButton.rotation += 180f
            mainList[position].isOpen = true
            mainList.addAll(position + 1, it)
            notifyItemRangeInserted(position + 1, it.size)
        }

    }

    private fun closeView(position: Int, imageButton: ImageButton) {
        mainList[position].sublist?.let { sublist ->
            imageButton.rotation -= 180f
            mainList[position].isOpen = false
            mainList.removeAll(sublist)
            notifyItemRangeRemoved(position + 1, sublist.size)
        }

    }

    inner class HeaderViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val container = view.rootParentContainer
        private val header = view.parentHeaderText
        private val arrow = view.expandImg

        fun bind(theoryItem: TheoryItem, position: Int) {
            container.setOnClickListener {
                openOrCloseView(position, arrow)
            }
            header.text = view.context.getString(R.string.lesson_header_text, theoryItem.title)
            arrow.rotation = if (theoryItem.isOpen) -270f else -90f
        }
    }

    inner class RootViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val container = view.rootParentContainer
        private val header = view.parentHeaderText
        private val arrow = view.expandImg

        fun bind(theoryItem: TheoryItem, position: Int) {
            container.setOnClickListener {
                openOrCloseView(position, arrow)
            }
            val layoutParams = header.layoutParams as MarginLayoutParams
            layoutParams.marginStart = 12
            header.text = theoryItem.title
            arrow.rotation = if (theoryItem.isOpen) -270f else -90f
        }
    }

    inner class ParentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val container = view.rootParentContainer
        private val header = view.parentHeaderText
        private val arrow = view.expandImg

        fun bind(theoryItem: TheoryItem, position: Int) {
            container.setOnClickListener {
                openOrCloseView(position, arrow)
            }
            val layoutParams = header.layoutParams as MarginLayoutParams
            layoutParams.marginStart = 20
            header.text = theoryItem.title
            arrow.rotation = if (theoryItem.isOpen) -270f else -90f
        }
    }

    inner class ChildViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val container = view.rootChildrenContainer
        private val header = view.childrenHeaderText

        fun bind(theoryItem: TheoryItem) {
            container.setOnClickListener {
                onTopicItemListener(theoryItem.id)
            }
            val layoutParams = header.layoutParams as MarginLayoutParams
            layoutParams.marginStart = 24
            header.text = theoryItem.title
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<TheoryItem>() {
        override fun areItemsTheSame(oldItem: TheoryItem, newItem: TheoryItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TheoryItem, newItem: TheoryItem): Boolean {
            return oldItem == newItem
        }
    }
}