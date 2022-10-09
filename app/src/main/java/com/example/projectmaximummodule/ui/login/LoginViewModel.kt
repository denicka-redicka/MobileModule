package com.example.projectmaximummodule.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectmaximummodule.application.AppSharedPreferences
import com.example.projectmaximummodule.application.BaseViewModel
import com.example.projectmaximummodule.data.network.interceptors.GetCookieTokenInterceptor
import com.example.projectmaximummodule.data.network.retorfit.LoginApiService
import com.example.projectmaximummodule.data.network.retorfit.MainApiService
import com.example.projectmaximummodule.data.network.retorfit.request.LoginRequest
import com.example.projectmaximummodule.data.network.retorfit.request.OauthRequest
import com.example.projectmaximummodule.data.network.retorfit.response.UserResponse
import com.example.projectmaximummodule.di.Oauth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val loginApi: LoginApiService,
    @Oauth val oauthApi: MainApiService,
    val sharedPreferences: AppSharedPreferences) : ViewModel() {

    private val loginMutableLiveData = MutableLiveData<UserResponse?>()
    val loginLiveData: LiveData<UserResponse?> = loginMutableLiveData

    private val handlerException = CoroutineExceptionHandler { coroutineContext, throwable ->
        loginMutableLiveData.postValue(null)
        Log.d("Network exception", "exception handled: ${throwable.message}")
    }

    private val coroutineScope = CoroutineScope(
        Dispatchers.IO
                + SupervisorJob()
                + handlerException
    )

    fun checkLogin(request: LoginRequest) {
        coroutineScope.launch {
            loginApi.login(request)
            val user = loginApi.checkAuth()
            if (user != null) {
                oauthApi.redirectToOauth()
                val oauthResponse = loginApi.authorize(GetCookieTokenInterceptor.state)
                val response = oauthResponse.separateResponse()
                val oauthRequest = OauthRequest(
                    code = response[0].substring(response[0].indexOf("=")+1),
                    session = response[2].substring(response[2].indexOf("=")+1),
                    state = response[1].substring(response[1].indexOf("=")+1),
                    fromUrl = "https://education.maximumtest.ru/"
                )
                oauthApi.oauthResult(oauthRequest)
                sharedPreferences.setAccessToken(GetCookieTokenInterceptor.token)
            }

            loginMutableLiveData.postValue(user)
        }
    }
}