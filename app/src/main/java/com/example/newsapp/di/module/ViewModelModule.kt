package com.example.newsapp.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.di.ViewModelKey
import com.example.newsapp.ui.BaseViewModelFactory
import com.example.newsapp.ui.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHoveViewModel(model: HomeViewModel):ViewModel

    @Binds
    abstract fun bindsBaseFactory(factory:BaseViewModelFactory):ViewModelProvider.Factory
}