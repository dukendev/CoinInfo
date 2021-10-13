package com.ysanjeet535.coininfo.di

import android.content.Context
import com.ysanjeet535.CoinInfoApplication
import com.ysanjeet535.coininfo.util.Constants.BASE_URL
import com.ysanjeet535.coininfo.data.remote.CoinApi
import com.ysanjeet535.coininfo.data.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext context: Context) : CoinInfoApplication {
        return context as CoinInfoApplication
    }

    @Singleton
    @Provides
    fun provideCoinApi() : CoinApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(CoinApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(coinApi: CoinApi) : CoinRepository {
        return CoinRepository(coinApi)
    }

}