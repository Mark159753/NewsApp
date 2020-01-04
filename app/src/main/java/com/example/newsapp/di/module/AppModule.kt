package com.example.newsapp.di.module

import android.app.Application
import android.content.Context
import com.example.newsapp.data.db.dao.NewsDao
import com.example.newsapp.data.network.ApiService
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.data.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(app:Application):Context{
        return app.applicationContext
    }

    @Provides
    @Singleton
    fun provideRepository(service:ApiService, newsDao: NewsDao):NewsRepository{
        return NewsRepositoryImpl(service, newsDao)
    }
}