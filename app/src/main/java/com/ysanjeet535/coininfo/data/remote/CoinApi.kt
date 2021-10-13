package com.ysanjeet535.coininfo.data.remote

import com.ysanjeet535.coininfo.data.dto.CoinItem
import com.ysanjeet535.coininfo.data.dto.CoinMetaData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinApi {

    @GET("prices")
    suspend fun getCoins(@Query("key") key:String,@Query("format") format:String) : Response<List<CoinItem>>

    @GET("currencies")
    suspend fun getCoinMetadata(@Query("key") key:String,@Query("ids") id : String) : Response<List<CoinMetaData>>
}