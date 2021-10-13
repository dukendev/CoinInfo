package com.ysanjeet535.coininfo.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ysanjeet535.coininfo.data.repository.CoinRepository

class MainViewModelFactory(private val repository: CoinRepository) : ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository = repository) as T
    }
}