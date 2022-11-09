package com.example.projectmaximummodule.core.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class RouterImpl @Inject constructor(): Router {

    private val mutableNavigationCommand = MutableLiveData<NavigationDestination>()
    override val navigationCommand: LiveData<NavigationDestination> = mutableNavigationCommand

    override fun navigateTo(destination: NavigationDestination) {
        mutableNavigationCommand.value = destination
    }
}