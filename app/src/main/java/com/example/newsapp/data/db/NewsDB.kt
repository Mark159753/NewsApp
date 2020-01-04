package com.example.newsapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsapp.data.db.dao.NewsDao
import com.example.newsapp.model.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class NewsDB: RoomDatabase() {

    abstract fun getNewsDao():NewsDao

    companion object{
        @Volatile
        private var instance:NewsDB? = null

        operator fun invoke(context: Context) = instance ?: synchronized(this){
            instance ?: buildDataBase(context).also{ instance = it}
        }

        private fun buildDataBase(context: Context) = Room.databaseBuilder(
            context, NewsDB::class.java, "NewsDB"
        ).build()
    }
}