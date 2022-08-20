package com.example.aisleyproject.model.repository

import com.example.aisleyproject.model.network.AisleyApiInterface
import com.example.aisleyproject.model.request.PhoneBodyRequest
import com.example.aisleyproject.model.request.VerifyPhoneRequest
import com.example.aisleyproject.utils.ApiResponse
import com.example.aisleyproject.utils.ConstantUtils
import com.example.aisleyproject.utils.Error
import com.example.aisleyproject.utils.Success
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AisleyRespository {

    private val aisleyApi = Retrofit.Builder().baseUrl(ConstantUtils.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()
        .create(AisleyApiInterface::class.java)


    suspend fun registerPhoneNumber(phoneBody: PhoneBodyRequest): ApiResponse {
        return try {
            val response = aisleyApi.registerNumber(phoneBody)
            if (response.isSuccessful) {
               Success(data = response.body())
            } else {
               Error(message = response.message())
            }
        } catch (e: Exception) {
         Error(message = e.message!!)
        }
    }

    suspend fun verifyPhoneNumber(verifyPhoneRequest: VerifyPhoneRequest):ApiResponse{
        return try{
            val response=aisleyApi.verifyNumber(verifyPhoneRequest)
            if (response.isSuccessful) {
                Success(data = response.body())
            } else {
                Error(message = response.message())
            }
        } catch (e:Exception){
            Error(message = e.message!!)
        }
    }

}
