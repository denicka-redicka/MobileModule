package com.example.projectmaximummodule.ui.profile

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import coil.load
import coil.transform.CircleCropTransformation
import com.example.projectmaximummodule.R
import com.example.projectmaximummodule.core.application.AppSharedPreferences
import com.example.projectmaximummodule.core.application.BaseViewModel
import com.example.projectmaximummodule.data.network.retorfit.response.ProfileResponse
import com.example.projectmaximummodule.data.network.retorfit.response.ShortPositionResponse
import com.example.projectmaximummodule.data.profile.ProfileRepository
import com.example.projectmaximummodule.util.RemoteResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val prefs: AppSharedPreferences
): BaseViewModel()  {

    private val profileMutableLiveData = MutableLiveData<RemoteResult<ProfileResponse, Throwable>>()
    val profileLiveData: LiveData<RemoteResult<ProfileResponse, Throwable>> = profileMutableLiveData

    private val shortListMutableLiveData = MutableLiveData<RemoteResult<List<ShortPositionResponse>, Throwable>>()
    val shortListLiveData: LiveData<RemoteResult<List<ShortPositionResponse>, Throwable>> = shortListMutableLiveData

    private var groupId = prefs.getGroupId()

    fun getProfile() {
        coroutineScope.launch {
            profileMutableLiveData.postValue(repository.getProfileInfo())
        }
    }

    fun provideProfileUi(view: View) {
        val user = (profileLiveData.value as RemoteResult.Success).value

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
                val shortList = repository.getShortList(groupId)
                shortListMutableLiveData.postValue(shortList)
            } else if (groupId != prefs.getGroupId()) {
                groupId = prefs.getGroupId()
                val shortList = repository.getShortList(groupId)
                shortListMutableLiveData.postValue(shortList)
            }
        }
    }

    fun getShortList(id: Long) {
        coroutineScope.launch {
            if (id != -1L && id != groupId) {
                groupId = id
                val shortList = repository.getShortList(id)
                shortListMutableLiveData.postValue(shortList)
            } else return@launch
        }
    }

    fun provideShortListUi(view: View) {
        val list = (shortListLiveData.value as RemoteResult.Success).value

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

    fun clearToken() {
        prefs.clearRefreshToken()
        prefs.clearAccessToken()
        prefs.clearSession()
    }
}