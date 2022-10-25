package com.example.projectmaximummodule.ui.customview

import android.content.Context
import android.os.SystemClock
import android.util.AttributeSet
import android.widget.Chronometer

class Timer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : Chronometer(context, attrs, defStyle) {

    var isAvailable = true

    fun getTimeCount(): Int {
        val minutes = text.substring(0, 2).toInt()
        val seconds = text.substring(3, 5).toInt()
        return minutes * 60 + seconds
    }

    override fun start() {
        if (isAvailable) super.start()
    }

    fun reset() {
        base = SystemClock.elapsedRealtime()
    }

    fun resetAndStart() {
        isAvailable = true
        reset()
        start()
    }

    fun setSpentTime(time: Int) {
        val minutes = time / 60
        val seconds = time - minutes
        text = "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
    }
}