package com.example.newsapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.model.Article
import com.example.newsapp.utils.lazyDeferred
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    suspend fun makeSearch(query:String):LiveData<List<Article>>{
        return repository.getSearch(query)
    }
}
