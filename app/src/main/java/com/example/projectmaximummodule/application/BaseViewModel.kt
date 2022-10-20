package com.example.projectmaximummodule.application

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

open class BaseViewModel: ViewModel() {

    private val handlerException = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.d("Network exception", "exception handled: ${throwable.message}")
    }

    protected val coroutineScope = CoroutineScope(
        Dispatchers.IO
                + SupervisorJob()
                + handlerException
    )


}