package com.example.newsapp.di.module

import com.example.newsapp.data.network.ApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideApiService():ApiService{
        return ApiService.invoke()
    }
}