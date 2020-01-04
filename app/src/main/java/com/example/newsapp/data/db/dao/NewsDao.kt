package com.example.newsapp.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.model.Article

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news:List<Article>)

    @Query("SELECT * FROM article")
    fun getNews():LiveData<List<Article>>

    @Query("DELETE FROM article")
    suspend fun deletrAll()
}