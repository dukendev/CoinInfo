package com.ysanjeet535.coininfo.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ysanjeet535.coininfo.data.dto.CoinItem
import com.ysanjeet535.coininfo.data.dto.CoinMetaData
import com.ysanjeet535.coininfo.data.remote.CoinApi
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class CoinRepository @Inject constructor
    (private val coinApi: CoinApi) {

    private val coinsList = MutableLiveData<List<CoinItem>>()

    val coins : LiveData<List<CoinItem>> get() = coinsList

    private val _coinMetadata = MutableLiveData<List<CoinMetaData>>()
    val coinMetaData : LiveData<List<CoinMetaData>> get() = _coinMetadata

    suspend fun getCoins(key:String,format : String){
        val result = coinApi.getCoins(key = key,format = format)
        if(result?.body() != null){
            coinsList.postValue(result.body())
        }
    }

    suspend fun getCoinMetaData(key:String,id : String){
        val result = coinApi.getCoinMetadata( key = key,id = id)
        if(result?.body() != null){
            _coinMetadata.postValue(result.body())
        }
    }
}