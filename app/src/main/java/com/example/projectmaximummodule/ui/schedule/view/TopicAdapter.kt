package com.example.projectmaximummodule.ui.schedule.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.data.network.retorfit.response.SubjectResponse
import kotlinx.android.synthetic.main.holder_topic.view.*

class TopicAdapter (
    private val topics: List<SubjectResponse>
    ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TopicViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.holder_topic, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TopicViewHolder).bind(topics[position])
    }

    override fun getItemCount(): Int {
        return topics.size
    }

    private class TopicViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val topicName: TextView = view.topicName
        private val theoryCount: TextView = view.theoryCountTxt
        private val videoCount: TextView = view.videoCountTxt
        private val practiceCount: TextView = view.practiceCountTxt

        fun bind(topic: SubjectResponse) {
            topicName.text = topic.title
            theoryCount.text = "${topic.statistics.theory.all}"
            videoCount.text = "${topic.statistics.video.all}"
            practiceCount.text = "${topic.statistics.practice.all}"
        }
    }
}