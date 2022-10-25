package com.example.projectmaximummodule.ui.login.view

import android.app.Activity
import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.application.AppSharedPreferences.Companion.ACCESS_TOKEN_KEY
import com.example.projectmaximummodule.application.AppSharedPreferences.Companion.SHARED_PREFS
import com.example.projectmaximummodule.data.network.retorfit.request.LoginRequest
import com.example.projectmaximummodule.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*

@AndroidEntryPoint
class LoginFragment: Fragment(R.layout.fragment_login) {


    private val viewModel: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = context?.getSharedPreferences(SHARED_PREFS, 0)
        if (!prefs?.getString(ACCESS_TOKEN_KEY, "").isNullOrEmpty()) {
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }

        viewModel.loginLiveData.observe(viewLifecycleOwner) { user ->
            loginButton.isEnabled = true
            loginButton.backgroundTintMode = PorterDuff.Mode.CLEAR
            if (user != null ) {
                findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
            } else {
                Toast.makeText(
                    context,
                    "Something wrong with email or password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        loginButton.setOnClickListener {
            it.isEnabled = false
            hideKeyboard()
            val inputUser = LoginRequest(
                loginInputText.text.toString(),
                passwordInputText.text.toString()
            )
            viewModel.checkLogin(inputUser)
        }
    }

    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}