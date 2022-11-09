package com.example.projectmaximummodule.core.application

import android.content.SharedPreferences
import javax.inject.Inject

class AppSharedPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun getAccessToken() = sharedPreferences.getString(ACCESS_TOKEN_KEY, "")

    fun setAccessToken(accessToken: String) {
        sharedPreferences.edit().putString(ACCESS_TOKEN_KEY, accessToken).apply()
    }

    fun getGroupId() = sharedPreferences.getLong(GROUP_ID, -1)

    fun setGroupId(id: Long) {
        sharedPreferences.edit().putLong(GROUP_ID, id).apply()
    }

    fun getTeacherName() = sharedPreferences.getString(TEACHER_NAME, "")

    fun setTeacherName(name: String) {
        sharedPreferences.edit().putString(TEACHER_NAME, name).apply()
    }

    fun getTeacherAvatar() = sharedPreferences.getString(TEACHER_AVATAR, "")

    fun setTeacherAvatar(url: String) {
        sharedPreferences.edit().putString(TEACHER_AVATAR, url).apply()
    }

    companion object {
        const val SHARED_PREFS = "APP_SHARED_PREFS"
        const val ACCESS_TOKEN_KEY = "access_token"
        private const val GROUP_ID = "group_id"
        private const val TEACHER_NAME = "teacher"
        private const val TEACHER_AVATAR = "teacher_avatar"
    }
}