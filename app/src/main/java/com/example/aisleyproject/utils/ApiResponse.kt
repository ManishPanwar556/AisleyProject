package com.example.aisleyproject.utils

abstract class ApiResponse {

}

data class Success<T>(val data:T): ApiResponse()


data class Error(val message:String):ApiResponse()

object Empty:ApiResponse()