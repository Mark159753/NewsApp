package com.example.newsapp.di.component

import android.app.Application
import com.example.newsapp.di.module.AppModule
import com.example.newsapp.di.module.DataBaseModule
import com.example.newsapp.di.module.NetworkModule
import com.example.newsapp.di.module.ViewModelModule
import com.example.newsapp.ui.home.HomeFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, DataBaseModule::class, NetworkModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(main:HomeFragment)

    @Component.Builder
    interface Builder{
        fun build():AppComponent

        @BindsInstance
        fun appContext(app:Application):Builder
    }
}