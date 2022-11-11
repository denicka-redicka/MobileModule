package com.example.projectmaximummodule.data.network.interceptors

import com.example.projectmaximummodule.core.application.AppSharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

class AddCookieTokenInterceptor (val prefs: AppSharedPreferences): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val tokenFromPrefs = (prefs.getAccessToken() ?: prefs.getRefreshToken())?: ""
        val cookieToken = tokenFromPrefs.ifEmpty { GetCookieTokenInterceptor.token }
        val request = chain.request()
            .newBuilder()
            .addHeader("cookie", cookieToken + "; " + GetCookieTokenInterceptor.session + "; state=" + GetCookieTokenInterceptor.state)
            .build()
        return chain.proceed(request)
    }
}