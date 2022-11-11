package com.example.projectmaximummodule.core.application

import android.content.SharedPreferences
import javax.inject.Inject

class AppSharedPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun getRefreshToken() = sharedPreferences.getString(REFRESH_TOKEN_KEY, "")

    fun setRefreshToken(refreshToken: String) {
        sharedPreferences.edit().putString(REFRESH_TOKEN_KEY, refreshToken).apply()
    }

    fun clearRefreshToken() {
        sharedPreferences.edit().remove(REFRESH_TOKEN_KEY).apply()
    }

    fun getAccessToken() = sharedPreferences.getString(ACCESS_TOKEN_KEY, null)

    fun setAccessToken(accessToken: String) {
        sharedPreferences.edit().putString(ACCESS_TOKEN_KEY, accessToken).apply()
    }

    fun clearAccessToken() {
        sharedPreferences.edit().remove(ACCESS_TOKEN_KEY).apply()
    }

    fun getSession() = sharedPreferences.getString(SESSION_KEY, null)

    fun setSession(session: String) {
        sharedPreferences.edit().putString(SESSION_KEY, session).apply()
    }

    fun clearSession() {
        sharedPreferences.edit().remove(SESSION_KEY).apply()
    }

    fun getGroupId() = sharedPreferences.getLong(GROUP_ID_KEY, -1)

    fun setGroupId(id: Long) {
        sharedPreferences.edit().putLong(GROUP_ID_KEY, id).apply()
    }

    fun getTeacherName() = sharedPreferences.getString(TEACHER_NAME_KEY, "")

    fun setTeacherName(name: String) {
        sharedPreferences.edit().putString(TEACHER_NAME_KEY, name).apply()
    }

    fun getTeacherAvatar() = sharedPreferences.getString(TEACHER_AVATAR_KEY, "")

    fun setTeacherAvatar(url: String) {
        sharedPreferences.edit().putString(TEACHER_AVATAR_KEY, url).apply()
    }

    companion object {
        const val SHARED_PREFS = "APP_SHARED_PREFS"
        const val REFRESH_TOKEN_KEY = "refresh_token"
        const val ACCESS_TOKEN_KEY = "access_token"
        const val SESSION_KEY = "session"
        private const val GROUP_ID_KEY = "group_id"
        private const val TEACHER_NAME_KEY = "teacher"
        private const val TEACHER_AVATAR_KEY = "teacher_avatar"
    }
}