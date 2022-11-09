package com.example.projectmaximummodule.core.navigation

import androidx.lifecycle.LiveData

interface Router {
    val navigationCommand : LiveData<NavigationDestination>
    fun navigateTo(destination: NavigationDestination)
}