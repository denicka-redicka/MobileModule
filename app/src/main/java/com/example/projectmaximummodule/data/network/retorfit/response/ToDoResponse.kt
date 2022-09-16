package com.example.projectmaximummodule.data.network.retorfit.response

import android.annotation.SuppressLint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.*

@Serializable
data class ToDoResponse (
        val type: String,
        val lesson: ToDoLesson? = null,
        val stats: List<ToDoStats>? = null,
        val dateStart: Long? = null,
        val title: String? = null,
        @SerialName("all_debts")
        val allDebts: Int? = null,
        @SerialName("paid_debts")
        val paidDebts: Int? = null,
        val connectLink: String? = null,
        val connectionStatus: String? = null,
        val duration: Int? = null,
        @SerialName("tests_count")
        val testsCount: Int? = null,
        @SerialName("tests_right_solved_count")
        val testsRightSolvedCount: Int? = null
) {
        fun getStatsTryPracticeCount(): Int {
                var tried = 0
                stats?.forEach{
                       tried += it.tried?: 0
                }
                return tried
        }

        fun getStatsAllPracticecont(): Int {
                var all = 0
                stats?.forEach{
                        all += it.all?: 0
                }
                return all
        }

        fun getResolveCount(): Int {
            return (allDebts?: 0) - (paidDebts?: 0)
        }

        @SuppressLint("SimpleDateFormat")
        fun getTimeStamp(): String {
                val sdf = SimpleDateFormat("dd.MM.yyyy HH.mm")
                return if (dateStart != null) {
                        val netDate = Date(dateStart * 1000)
                        sdf.format(netDate)
                } else {
                        ""
                }
        }

}

@Serializable
data class ToDoLesson (
        val id: Long? = null,
        val subjectId: Long ? = null,
        val type: String? = null
        )

@Serializable
data class ToDoStats (
        val all: Int? = null,
        @SerialName("try")
        val tried: Int? = null,
        val text: String? = null
)



