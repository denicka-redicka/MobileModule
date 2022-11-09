package com.example.projectmaximummodule.ui.profile.view

import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.core.application.SelectGroupReceiver
import com.example.projectmaximummodule.ui.profile.ProfileViewModel
import com.example.projectmaximummodule.util.RemoteResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_profile.*

@AndroidEntryPoint
class ProfileFragment: Fragment(R.layout.fragment_profile), SelectGroupReceiver.OnGroupSelectedListener {

    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var selectGroupReceiver: SelectGroupReceiver

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeButton.setOnClickListener {
            viewModel.clearToken()
        }

        viewModel.profileLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is RemoteResult.Success -> viewModel.provideProfileUi(view)
                is RemoteResult.Failed -> TODO("notice user that we have problems")
            }
        }

        viewModel.shortListLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is RemoteResult.Success -> viewModel.provideShortListUi(view)
                is RemoteResult.Failed -> TODO("notice user that we have problems")
            }

        }
        viewModel.getProfile()

        viewModel.updateShortList()
    }

    override fun onResume() {
        super.onResume()
        selectGroupReceiver = SelectGroupReceiver(this)
        context?.registerReceiver(selectGroupReceiver, IntentFilter().apply {
            addAction(SelectGroupReceiver.GROUP_SELECTED)
        })
    }

    override fun onPause() {
        super.onPause()
        context?.unregisterReceiver(selectGroupReceiver)
    }

    override fun onGroupSelected(id: Long) {
        viewModel.getShortList(id)
    }
}