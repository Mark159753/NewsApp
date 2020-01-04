package com.example.newsapp.ui.home

import androidx.lifecycle.ViewModel
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.utils.lazyDeferred
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    val news by lazyDeferred {
        repository.getArticles()
    }
}
