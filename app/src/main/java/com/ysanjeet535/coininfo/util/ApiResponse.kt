package com.ysanjeet535.coininfo.util

sealed class ApiResponse<out T>(val data : T? = null,message : String? = null){
    class Success<out T>(data:T) : ApiResponse<T>(data)
    class Error<out T>(message: String,data: T?=null) : ApiResponse<T>(data, message)
    class Loading<out T>(data: T?=null) : ApiResponse<T>(data)
}