package com.example.aisleyproject.viewModel

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aisleyproject.model.repository.AisleyRespository
import com.example.aisleyproject.model.request.PhoneBodyRequest
import com.example.aisleyproject.model.request.VerifyPhoneRequest
import com.example.aisleyproject.utils.ApiResponse
import com.example.aisleyproject.utils.Empty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AisleyViewModel : ViewModel() {
    private val repository = AisleyRespository()

    private val _timer = MutableLiveData<String>()
    val timer: LiveData<String> = _timer

    private val _phoneVerifiedData = MutableLiveData<ApiResponse>()
    val phoneVerifiedData: LiveData<ApiResponse> = _phoneVerifiedData

    private val _phoneRegisterData = MutableLiveData<ApiResponse>()
    val phoneRegisterData: LiveData<ApiResponse> = _phoneRegisterData

    private val _userData=MutableLiveData<ApiResponse>()
    val userData:LiveData<ApiResponse> = _userData


    private val timerCallback = object : CountDownTimer(60000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            val res = millisUntilFinished / 1000
            if (res >= 10) {
                _timer.postValue("00:${res}")
            } else {
                _timer.postValue("00:0${res}")
            }
        }

        override fun onFinish() {
            _timer.postValue("00:00")
        }

    }

    fun emptyPhoneRegisterLiveData() {
        _phoneRegisterData.postValue(Empty)
    }

    fun registerNumber(phone: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.registerPhoneNumber(PhoneBodyRequest(phone))
            withContext(Dispatchers.Main) {
                _phoneRegisterData.postValue(response)
            }
        }
    }

    fun verifyNumber(phone: String, otp: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response =
                repository.verifyPhoneNumber(VerifyPhoneRequest(number = phone, otp = otp))
            withContext(Dispatchers.Main) {
                _phoneVerifiedData.postValue(response)
            }
        }
    }

    fun startTimer() {
        timerCallback.start()
    }

    fun getUserData(token:String){
        viewModelScope.launch(Dispatchers.IO) {
            val response=repository.getUserDetails(token)
            withContext(Dispatchers.IO){
                _userData.postValue(response)
            }
        }
    }


}