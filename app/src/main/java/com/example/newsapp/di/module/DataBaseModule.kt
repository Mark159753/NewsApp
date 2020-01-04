package com.example.newsapp.di.module

import android.content.Context
import com.example.newsapp.data.db.NewsDB
import com.example.newsapp.data.db.dao.NewsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DataBaseModule {

    @Provides
    @Singleton
    fun provideDB(context: Context):NewsDB{
        return NewsDB.invoke(context)
    }

    @Provides
    @Singleton
    fun provideNewsDao(db:NewsDB):NewsDao{
        return db.getNewsDao()
    }
}