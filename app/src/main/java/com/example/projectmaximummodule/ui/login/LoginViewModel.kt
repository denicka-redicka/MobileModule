package com.example.projectmaximummodule.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.projectmaximummodule.core.application.AppSharedPreferences
import com.example.projectmaximummodule.core.application.BaseViewModel
import com.example.projectmaximummodule.data.auth.AuthRepository
import com.example.projectmaximummodule.data.auth.remote.request.LoginRequest
import com.example.projectmaximummodule.util.RemoteResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
) : BaseViewModel() {

    private val loginMutableLiveData = MutableLiveData<RemoteResult<Unit, Throwable>>()
    val loginLiveData: LiveData<RemoteResult<Unit, Throwable>> = loginMutableLiveData

    fun login(request: LoginRequest) {
        coroutineScope.launch {
            loginMutableLiveData.postValue(repository.login(request))
        }
    }
}