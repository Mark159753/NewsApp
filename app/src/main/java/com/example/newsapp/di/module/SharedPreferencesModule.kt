package com.example.newsapp.di.module

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.example.newsapp.data.db.dao.NewsDao
import com.example.newsapp.data.network.ApiService
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.data.repository.NewsRepositoryImpl
import com.example.newsapp.di.component.FragmentComponent
import dagger.Module
import dagger.Provides

@Module
class SharedPreferencesModule {

    @Provides
    fun providePreferences(activity: FragmentActivity): SharedPreferences {
        return activity.getPreferences(Context.MODE_PRIVATE)
    }

    @Provides
    fun provideRepository(service: ApiService, newsDao: NewsDao, preferences: SharedPreferences): NewsRepository {
        return NewsRepositoryImpl(service, newsDao, preferences)
    }
}