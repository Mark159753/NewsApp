package com.example.newsapp

import android.app.Application
import com.example.newsapp.di.component.AppComponent
import com.example.newsapp.di.component.DaggerAppComponent

class NewsApp:Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appContext(this).build()
    }

    fun getAppComponent() = appComponent
}