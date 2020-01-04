package com.example.newsapp.model


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Article")
data class Article(
    var viewType:Int?,
    var category:String?,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    @Embedded(prefix = "source_")
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
){
    @PrimaryKey(autoGenerate = true)
    var key:Int? = null
}