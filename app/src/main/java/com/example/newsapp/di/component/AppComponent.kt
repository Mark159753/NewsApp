package com.example.newsapp.di.component

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.di.module.*
import com.example.newsapp.ui.home.HomeFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, DataBaseModule::class, NetworkModule::class, ViewModelModule::class])
interface AppComponent {

    fun fragmentComponent():FragmentComponent.Builder

    @Component.Builder
    interface Builder{
        fun build():AppComponent

        @BindsInstance
        fun appContext(app:Application):Builder
    }
}