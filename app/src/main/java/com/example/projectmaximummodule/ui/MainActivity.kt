package com.example.projectmaximummodule.ui

import android.graphics.Color
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.ui.splash.ui.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (VERSION.SDK_INT >= VERSION_CODES.S) {
            viewModel.navigate()
            val content: View = findViewById(android.R.id.content)
            val onPreDrawListener = ViewTreeObserver.OnPreDrawListener { false }
            content.viewTreeObserver.addOnPreDrawListener(onPreDrawListener)
            viewModel.checkLoginLiveData.observe(this) { isReady ->
                content.viewTreeObserver.removeOnPreDrawListener(onPreDrawListener)
            }
        }

        window.apply {
            statusBarColor = Color.TRANSPARENT
        }
    }
}