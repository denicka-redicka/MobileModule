package com.example.projectmaximummodule.data.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class GetCookieTokenInterceptor : Interceptor {

    companion object {
        var token = ""
        var session = ""
        var state = ""
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        val header = originalResponse.headers("set-cookie")
        if (header.isNotEmpty()) {
            if (header[0].contains("token")) {
                token = header[0].substring(0, header[0].indexOf(';'))
            } else if (header[0].contains("state")) {
                state = header[0].substring(header[0].indexOf('=')+1, header[0].indexOf(';'))
            }
            if(header.size > 1) session =  header[1].substring(0, header[1].indexOf(';'))
        }
        return originalResponse
    }
}