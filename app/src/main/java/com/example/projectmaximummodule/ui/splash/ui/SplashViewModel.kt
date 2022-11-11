package com.example.projectmaximummodule.ui.splash.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.core.application.AppSharedPreferences
import com.example.projectmaximummodule.core.application.BaseViewModel
import com.example.projectmaximummodule.data.auth.AuthRepository
import com.example.projectmaximummodule.util.RemoteResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val prefs: AppSharedPreferences
) : BaseViewModel() {

    private companion object {
        private const val MOVE_TO_APP = R.id.action_splashFragment_to_mainFragment
        private const val MOVE_TO_LOGIN = R.id.action_splashFragment_to_loginFragment
    }

    private val mutableCheckLoginLiveData = MutableLiveData<Int>()
    val checkLoginLiveData: LiveData<Int> = mutableCheckLoginLiveData

    fun navigate() {
        if (!prefs.getAccessToken().isNullOrEmpty()) {
            coroutineScope.launch {
                when (repository.checkLogin()) {
                    is RemoteResult.Success -> mutableCheckLoginLiveData.postValue(MOVE_TO_APP)
                    is RemoteResult.Failed -> mutableCheckLoginLiveData.postValue(MOVE_TO_LOGIN)
                }
            }
        } else
            mutableCheckLoginLiveData.value = MOVE_TO_LOGIN
    }
}