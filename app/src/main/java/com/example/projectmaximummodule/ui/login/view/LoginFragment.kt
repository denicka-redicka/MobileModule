package com.example.projectmaximummodule.ui.login.view

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.data.auth.remote.request.LoginRequest
import com.example.projectmaximummodule.ui.login.LoginViewModel
import com.example.projectmaximummodule.util.RemoteResult
import com.example.projectmaximummodule.util.hideKeyboard
import com.example.projectmaximummodule.util.showSystemMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*

@AndroidEntryPoint
class LoginFragment: Fragment(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loginLiveData.observe(viewLifecycleOwner) { result ->
            loginButton.isEnabled = true
            loginButton.backgroundTintMode = PorterDuff.Mode.CLEAR
            when (result) {
                is RemoteResult.Success -> findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                is RemoteResult.Failed -> showSystemMessage("Something goes wrong")
            }
        }

        loginButton.setOnClickListener { button ->
            button.isEnabled = false
            button.hideKeyboard()
            val inputUser = LoginRequest(
                loginInputText.text.toString(),
                passwordInputText.text.toString()
            )
            viewModel.login(inputUser)
        }
    }
}