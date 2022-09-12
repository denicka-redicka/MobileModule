package com.example.projectmaximummodule.application

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class SelectGroupReceiver (val groupSelectedListener: OnGroupSelectedListener?): BroadcastReceiver() {

    companion object {
        const val GROUP_SELECTED = "education_group_selected"
        const val SELECTED_GROUP_ID = "selected_group_id"
        const val UNKNOWN_GROUP: Long = -1
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val id = intent?.getLongExtra(SELECTED_GROUP_ID,  UNKNOWN_GROUP)
        groupSelectedListener?.onGroupSelected(id?: UNKNOWN_GROUP)
    }

    interface OnGroupSelectedListener {

        fun onGroupSelected(id: Long)
    }

}