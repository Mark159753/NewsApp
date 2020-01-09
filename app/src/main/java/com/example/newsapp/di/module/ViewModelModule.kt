package com.example.newsapp.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.di.ViewModelKey
import com.example.newsapp.ui.BaseViewModelFactory
import com.example.newsapp.ui.home.HomeViewModel
import com.example.newsapp.ui.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(model: HomeViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(model: SearchViewModel):ViewModel

    @Binds
    abstract fun bindsBaseFactory(factory:BaseViewModelFactory):ViewModelProvider.Factory
}