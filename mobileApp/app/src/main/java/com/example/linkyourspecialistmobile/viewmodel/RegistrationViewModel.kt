package com.example.linkyourspecialistmobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linkyourspecialistmobile.data.HomeRepository
import com.example.linkyourspecialistmobile.data.RegistrationResponseModel
import com.example.linkyourspecialistmobile.data.RegistrationRequestModel

class RegistrationViewModel : ViewModel() {
    private var homeRepository: HomeRepository? = null
    var registrationLiveData: LiveData<RegistrationResponseModel>? = null

    init {
        homeRepository = HomeRepository()
        registrationLiveData = MutableLiveData()
    }

    fun signup(registrationRequestModel: RegistrationRequestModel) {
        registrationLiveData = homeRepository?.signup(registrationRequestModel)
    }
}
