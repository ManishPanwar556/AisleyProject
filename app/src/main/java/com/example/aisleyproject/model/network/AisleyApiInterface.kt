package com.example.aisleyproject.model.network

import com.example.aisleyproject.model.request.PhoneBodyRequest
import com.example.aisleyproject.model.request.VerifyPhoneRequest
import com.example.aisleyproject.model.response.RegisterPhoneResponse
import com.example.aisleyproject.model.response.VerifyPhoneResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AisleyApiInterface {

    @POST("users/phone_number_login")
    suspend fun registerNumber(@Body phoneBody:PhoneBodyRequest):Response<RegisterPhoneResponse>

    @POST("users/verify_otp")
    suspend fun verifyNumber(@Body verifyPhoneRequest: VerifyPhoneRequest):Response<VerifyPhoneResponse>
}