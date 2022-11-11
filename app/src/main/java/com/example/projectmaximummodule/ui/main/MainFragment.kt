package com.example.projectmaximummodule.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.core.application.SelectGroupReceiver.Companion.GROUP_SELECTED
import com.example.projectmaximummodule.core.application.SelectGroupReceiver.Companion.SELECTED_GROUP_ID
import com.example.projectmaximummodule.util.isGone
import com.example.projectmaximummodule.util.isVisible
import com.example.projectmaximummodule.util.toGone
import com.example.projectmaximummodule.util.toVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main), AdapterView.OnItemSelectedListener,
    NavController.OnDestinationChangedListener {

    private companion object {
        private val DestinationWithoutBottomMenu =
            listOf(R.id.homeworkItemFragment, R.id.chatItemFragment)
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController =
            (childFragmentManager.findFragmentById(R.id.mainContainerView) as NavHostFragment).navController
        navController.addOnDestinationChangedListener(this)
        bottomNavigation.setupWithNavController(navController)

        viewModel.groupsLiveData.observe(viewLifecycleOwner) { groups ->
            val groupsTitles = groups.map { response ->
                response.educationCourse.title
            }
            groupsPicker.adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, groupsTitles)
        }

        groupsPicker.onItemSelectedListener = this
        if (savedInstanceState == null) {
            viewModel.getGroupList()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val groupId = viewModel.onGroupSelected(position)
        val intent = Intent(GROUP_SELECTED).putExtra(SELECTED_GROUP_ID, groupId)
        context?.sendBroadcast(intent)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        if (bottomNavigation.isVisible() && destination.id in DestinationWithoutBottomMenu) {
            bottomNavigation.toGone()
            topLayout.toGone()

        } else if (bottomNavigation.isGone()) {
            bottomNavigation.toVisible()
            topLayout.toVisible()
        }
    }
}