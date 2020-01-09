package com.example.newsapp.di.component

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.example.newsapp.di.module.SharedPreferencesModule
import com.example.newsapp.ui.home.HomeFragment
import com.example.newsapp.ui.search.SearchFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [SharedPreferencesModule::class])
interface FragmentComponent {

    fun inject(main:HomeFragment)
    fun inject(main:SearchFragment)

    @Subcomponent.Builder
    interface Builder{

        @BindsInstance
        fun plusActivity(activity: FragmentActivity):Builder

        fun create():FragmentComponent
    }
}