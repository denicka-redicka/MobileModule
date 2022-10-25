package com.example.projectmaximummodule.ui.debts.view

import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.projectmaximummodule.ui.customview.Timer

class ExercisePageChangedCallback: OnPageChangeCallback() {

    val chronometerList = hashMapOf<Int, Timer>()
    private var previousPosition = -1

    override fun onPageSelected(position: Int) {
        super.onPageSelected(position)

        val chronometer = chronometerList[position]
        chronometer?.reset()
        chronometer?.start()

        if (previousPosition != -1) {
            chronometerList[previousPosition]?.stop()
        }
        previousPosition = position
    }
}