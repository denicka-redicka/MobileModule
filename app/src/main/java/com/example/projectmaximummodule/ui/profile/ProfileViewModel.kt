package com.example.projectmaximummodule.ui.profile

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import coil.load
import coil.transform.CircleCropTransformation
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.application.AppSharedPreferences
import com.example.projectmaximummodule.application.BaseViewModel
import com.example.projectmaximummodule.data.network.retorfit.MainApiService
import com.example.projectmaximummodule.data.network.retorfit.response.ProfileResponse
import com.example.projectmaximummodule.data.network.retorfit.response.ShortPositionResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val api: MainApiService,
    val prefs: AppSharedPreferences): BaseViewModel()  {

    private val profileMutableLiveData = MutableLiveData<ProfileResponse>()
    val profileLiveData: LiveData<ProfileResponse> = profileMutableLiveData

    private val shortListMutableLiveData = MutableLiveData<List<ShortPositionResponse>>()
    val shortListLiveData: LiveData<List<ShortPositionResponse>> = shortListMutableLiveData

    private var groupId = prefs.getGroupId()

    fun getProfile() {
        coroutineScope.launch {
            val me = api.getProfile()
            profileMutableLiveData.postValue(me)
        }
    }

    fun provideProfileUi(view: View) {
        val user = profileLiveData.value ?: return

        view.nameText.text = "${user.firstName} ${user.lastName}"
        view.emailText.text = user.email
        view.phoneText.text = user.phone ?: ""

        view.avatarImg.load(user.avatar ?: "") {
            crossfade(true)
            transformations(CircleCropTransformation())
        }

        view.statsLayout.visibility = if(user.role == "student") View.VISIBLE else View.GONE

        view.changeButton.setOnClickListener {
            clearToken()
        }
    }

    fun updateShortList() {
        coroutineScope.launch {
            if (shortListLiveData.value == null) {
                val shortList = api.getShortList(groupId)
                shortListMutableLiveData.postValue(shortList)
            } else if (groupId != prefs.getGroupId()) {
                groupId = prefs.getGroupId()
                val shortList = api.getShortList(groupId)
                shortListMutableLiveData.postValue(shortList)
            }
        }
    }

    fun getShortList(id: Long) {
        coroutineScope.launch {
            if (id != -1L && id != groupId) {
                groupId = id
                val shortList = api.getShortList(id)
                shortListMutableLiveData.postValue(shortList)
            } else return@launch
        }
    }

    fun provideShortListUi(view: View) {
        val list = shortListLiveData.value ?: return

        view.firstPositionName.text = list[0].user.getFullName()
        view.firstPositionSummary.text = list[0].summary.toString()

        view.secondPositionName.text = list[1].user.getFullName()
        view.secondPositionSummary.text = list[1].summary.toString()

        view.firdPositionName.text = list[2].user.getFullName()
        view.firdPositionSummary.text = list[2].summary.toString()

        view.lastPositionName.text = list[3].user.getFullName()
        view.lastPositionSummary.text = list[3].summary.toString()
        view.lastPositionNumber.text = list[3].position.toString()

        view.mySummary.text = list[3].summary.toString()
        view.rankedText.text = view.context.getString(R.string.your_ranked, list[3].position)
    }

    private fun clearToken() {
        prefs.setAccessToken("")
    }
}