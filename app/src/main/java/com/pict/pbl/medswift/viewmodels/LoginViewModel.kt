package com.pict.pbl.medswift.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    val errorMessageFlag = MutableLiveData( false )
    val errorMessage = MutableLiveData( "" )
    val isLoading = MutableLiveData( false )

}