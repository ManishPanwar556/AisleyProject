package com.example.aisleyproject.model.network

import com.example.aisleyproject.model.request.PhoneBodyRequest
import com.example.aisleyproject.model.request.VerifyPhoneRequest
import com.example.aisleyproject.model.response.RegisterPhoneResponse
import com.example.aisleyproject.model.response.VerifyPhoneResponse
import com.example.aisleyproject.model.response.userData.UserDataResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AisleyApiInterface {

    @POST("users/phone_number_login")
    suspend fun registerNumber(@Body phoneBody:PhoneBodyRequest):Response<RegisterPhoneResponse>

    @POST("users/verify_otp")
    suspend fun verifyNumber(@Body verifyPhoneRequest: VerifyPhoneRequest):Response<VerifyPhoneResponse>

    @GET("users/test_profile_list")
    suspend fun getUserData(@Header("Authorization") token:String):Response<UserDataResponse>
}