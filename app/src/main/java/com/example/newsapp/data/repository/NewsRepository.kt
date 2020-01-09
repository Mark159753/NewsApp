package com.example.newsapp.data.repository

import androidx.lifecycle.LiveData
import com.example.newsapp.model.Article

interface NewsRepository {

    suspend fun getArticles():LiveData<List<Article>>

    suspend fun getSearch(query:String):LiveData<List<Article>>
}