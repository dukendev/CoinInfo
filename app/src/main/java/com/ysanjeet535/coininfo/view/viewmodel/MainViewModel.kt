package com.ysanjeet535.coininfo.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ysanjeet535.coininfo.util.Constants.API_KEY
import com.ysanjeet535.coininfo.data.dto.CoinItem
import com.ysanjeet535.coininfo.data.dto.CoinMetaData
import com.ysanjeet535.coininfo.data.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val repository: CoinRepository) : ViewModel() {

    val coins : LiveData<List<CoinItem>> get() = repository.coins
    val coinMetadata : LiveData<List<CoinMetaData>> get() = repository.coinMetaData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCoins(API_KEY,"json")
        }
    }

    fun getMetadata(id:String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCoinMetaData(API_KEY,id)
        }
    }
}