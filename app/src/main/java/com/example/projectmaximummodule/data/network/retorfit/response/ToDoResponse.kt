package com.example.projectmaximummodule.data.network.retorfit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
        val connectionLink: String? = null,
        val connectionStatus: String? = null,
        val duration: Int? = null,
        @SerialName("tests_count")
        val testsCount: Int? = null,
        @SerialName("tests_right_solved_count")
        val testsRightSolvedCount: Int? = null
)

@Serializable
data class ToDoLesson (
        val id: Long? = null,
        val subject: Long ? = null,
        val type: String? = null
        )

@Serializable
data class ToDoStats (
        val all: Int? = null,
        @SerialName("try")
        val tried: Long? = null,
        val text: String? = null
)



