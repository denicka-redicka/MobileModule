package com.example.projectmaximummodule.ui.debts.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.data.network.retorfit.response.DebtsResponse
import kotlinx.android.synthetic.main.holder_debt_child.view.*
import kotlinx.android.synthetic.main.holder_debt_count.view.*
import kotlinx.android.synthetic.main.holder_debt_parent.view.*

class HomeworkAdapter(
    private val onTopicClickListener: (curriculumId: Long, lessonId: Long) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var debts: MutableList<DebtsResponse.DebtsItems> = mutableListOf()
    var allDebtsCount = 0

    private companion object {
        private const val ADDITIONAL_VIEW_COUNT = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.holder_debts_title -> HeaderViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.holder_debts_title, parent, false)
            )
            R.layout.holder_debts_body -> BodyViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.holder_debts_body, parent, false)
            )
            R.layout.holder_debt_count -> CountViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.holder_debt_count, parent, false)
            )
            R.layout.holder_debt_parent -> DebtsParentViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.holder_debt_parent, parent, false)
            )
            R.layout.holder_debt_child -> DebtsChildViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.holder_debt_child, parent, false), onTopicClickListener
            )
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {}
            is BodyViewHolder -> {}
            is CountViewHolder -> holder.bind(allDebtsCount)
            is DebtsParentViewHolder -> holder.bind(debts[position - ADDITIONAL_VIEW_COUNT])
            is DebtsChildViewHolder -> holder.bind(debts[position - ADDITIONAL_VIEW_COUNT])
        }
    }

    override fun getItemCount(): Int {
        return debts.size + ADDITIONAL_VIEW_COUNT
    }

    override fun getItemViewType(position: Int): Int {
        return if (position in 0..2) {
            when (position) {
                0 -> R.layout.holder_debts_title
                1 -> R.layout.holder_debts_body
                2 -> R.layout.holder_debt_count
                else -> throw IllegalArgumentException()
            }
        } else if (debts[position - ADDITIONAL_VIEW_COUNT].knowledgeBaseSectionId == null)
            R.layout.holder_debt_parent
        else R.layout.holder_debt_child
    }

    fun openOrCloseView(position: Int, imageButton: ImageButton) {
        if (debts[position].isOpen) {
            closeView(position, imageButton)
        } else {
            openView(position, imageButton)
        }
    }

    private fun openView(position: Int, imageButton: ImageButton) {
        val lesson = debts[position]
        val items = lesson.lessonsDebtsItems
        if (items.isEmpty()) {
            lesson.curriculumSubjects?.forEach {
                items.add(it.value.convertToDebtsItem(lesson.lessonId ?: -1))
            }
        }
        imageButton.rotation += 180f
        debts[position].isOpen = true
        debts.addAll(position + 1, items)
        notifyItemRangeInserted(position + ADDITIONAL_VIEW_COUNT + 1, items.size)
    }

    private fun closeView(position: Int, imageButton: ImageButton) {
        val items = debts[position].lessonsDebtsItems
        imageButton.rotation -= 180f
        debts[position].isOpen = false
        debts.removeAll(items)
        notifyItemRangeRemoved(position + ADDITIONAL_VIEW_COUNT + 1, items.size)
    }

    private inner class DebtsParentViewHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {

        private val title = view.debtsLessonTitle
        private val timeStamp = view.debtsLessonTimeStamp
        private val count = view.lessonDebtsCount
        private val arrow = view.debtsExpandImg

        fun bind(debtsItem: DebtsResponse.DebtsItems) {
            view.setOnClickListener {
                openOrCloseView(debts.indexOf(debtsItem), arrow)
            }
            title.text = debtsItem.title
            timeStamp.text = debtsItem.getTimeStamp()
            count.text = "${debtsItem.debtsCount}"
            arrow.rotation = if (debtsItem.isOpen) -270f else -90f
        }

    }

    private class DebtsChildViewHolder(
        private val view: View,
        private val clickListener: (curriculumId: Long, lessonId: Long) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val title = view.topicTitle
        private val count = view.topicDebtsCount

        fun bind(debtsItem: DebtsResponse.DebtsItems) {

            view.setOnClickListener {
                clickListener(debtsItem.curriculumSubjectId ?: -1, debtsItem.lessonId ?: -1)
            }

            title.text = debtsItem.title
            count.text = "${debtsItem.debtsCount}"
        }

    }

    private class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private class BodyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private class CountViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val countView = view.debtsCount

        fun bind(count: Int) {
            countView.text = "$count"
        }
    }
}