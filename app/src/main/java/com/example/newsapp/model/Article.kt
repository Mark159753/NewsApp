package com.example.newsapp.model


import com.google.gson.annotations.SerializedName

data class Article(
    val viewType:Int?,
    val category:String?,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)