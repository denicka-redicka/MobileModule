package com.example.projectmaximummodule.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.application.SelectGroupReceiver.Companion.GROUP_SELECTED
import com.example.projectmaximummodule.application.SelectGroupReceiver.Companion.SELECTED_GROUP_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.view.*

@AndroidEntryPoint
class MainFragment: Fragment(R.layout.fragment_main), AdapterView.OnItemSelectedListener {

    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = (childFragmentManager.findFragmentById(R.id.mainContainerView) as NavHostFragment).navController
        view.bottomNavigation.setupWithNavController(navController)

        viewModel.groupsLiveData.observe(viewLifecycleOwner) { groups ->
            val groupsTitle = ArrayList<String>(groups.size)
            groups.forEach { groupResponse ->
                groupsTitle.add(groupResponse.educationCourse.title)
            }
            view.groupsPicker.adapter =  ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, groupsTitle)
        }

        view.groupsPicker.onItemSelectedListener = this

        viewModel.getGroupList()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val groupId = viewModel.onGroupSelected(position)
        val intent = Intent(GROUP_SELECTED).putExtra(SELECTED_GROUP_ID, groupId)
        context?.sendBroadcast(intent)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
}