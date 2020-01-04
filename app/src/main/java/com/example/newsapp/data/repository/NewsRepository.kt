package com.example.newsapp.data.repository

import androidx.lifecycle.LiveData
import com.example.newsapp.model.Article

interface NewsRepository {

    suspend fun getArticles():LiveData<List<Article>>

}