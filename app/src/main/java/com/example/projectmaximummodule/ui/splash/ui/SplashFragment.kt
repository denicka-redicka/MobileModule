package com.example.projectmaximummodule.ui.splash.ui

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.projectmaximummodule.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment: Fragment(R.layout.fragment_splash) {

    private val viewModel: SplashViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.checkLoginLiveData.observe(viewLifecycleOwner) { action ->
            findNavController().navigate(action)
        }

        if (savedInstanceState == null && VERSION.SDK_INT < VERSION_CODES.S) {
            viewModel.navigate()
        }
    }
}