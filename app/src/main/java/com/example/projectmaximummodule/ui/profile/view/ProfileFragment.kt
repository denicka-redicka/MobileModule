package com.example.projectmaximummodule.ui.profile.view

import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.application.SelectGroupReceiver
import com.example.projectmaximummodule.ui.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment: Fragment(R.layout.fragment_profile), SelectGroupReceiver.OnGroupSelectedListener {

    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var selectGroupReceiver: SelectGroupReceiver

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.profileLiveData.observe(viewLifecycleOwner) {
            viewModel.provideProfileUi(view)
        }

        viewModel.shortListLiveData.observe(viewLifecycleOwner) {
            viewModel.provideShortListUi(view)
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

    override fun onStop() {
        super.onStop()
        context?.unregisterReceiver(selectGroupReceiver)
    }

    override fun onGroupSelected(id: Long) {
        viewModel.getShortList(id)
    }
}