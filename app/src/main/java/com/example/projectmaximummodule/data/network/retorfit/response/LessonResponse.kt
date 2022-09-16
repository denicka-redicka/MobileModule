package com.example.projectmaximummodule.data.network.retorfit.response

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.*

@Serializable
data class ScheduleResponse (
    val items: List<LessonResponse>,
    val nextItemsCount: Int,
    val previousItemsCount: Int
    )

@Serializable
data class LessonResponse (
    val dateStart: Long,
    val duration: Long,
    val isActual: Boolean,
    val lessonType: String,
    val place: PlaceResponse,
    val presenceStatus: String,
    val subjects: List<SubjectResponse>,
    val progress: Int,
    val title: String,
) {

    @SuppressLint("SimpleDateFormat")
    fun getDateString(): String {
        val sdf = SimpleDateFormat("EEEE dd.MM")
        val date = Date(dateStart * 1000)
        val dateString = sdf.format(date)
        return dateString.replaceFirstChar { it.uppercase() }
    }

    @SuppressLint("SimpleDateFormat")
    fun getTiming(): String {
        val sdf = SimpleDateFormat("HH.mm")
        val timeStart = Date(dateStart * 1000)
        val timeEnd = Date((dateStart + duration * 60) * 1000 )
        return "${sdf.format(timeStart)} - ${sdf.format(timeEnd)} "
    }
}