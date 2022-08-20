package com.example.aisleyproject.model.request

data class VerifyPhoneRequest(
    val number:String,
    val otp:String
)