package com.example.newsapp.data.repository

import android.content.SharedPreferences
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.newsapp.data.db.dao.NewsDao
import com.example.newsapp.data.network.ApiService
import com.example.newsapp.data.network.response.safeApiCall
import com.example.newsapp.model.Article
import com.example.newsapp.ui.ViewTypes
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

const val TIME_REQUEST = "check_time"

class NewsRepositoryImpl @Inject constructor(
    private val apiServer:ApiService,
    private val newsDao: NewsDao,
    private val sharedPreferences: SharedPreferences
): NewsRepository {

    override suspend fun getArticles(): LiveData<List<Article>> {
        return withContext(Dispatchers.IO){
            initHeadNews()
            return@withContext newsDao.getNews()
        }
    }

    override suspend fun getSearch(query: String): LiveData<List<Article>> {
        return withContext(Dispatchers.IO){
            return@withContext liveData {
                val list = safeApiCall(Dispatchers.IO){apiServer.getSearch(query)}
                emit(if (list!= null) list.articles else emptyList<Article>())
            }
        }
    }

    private suspend fun initHeadNews(){
        val time = sharedPreferences.getLong(TIME_REQUEST, 0)

        if (time.equals(0)) {
            fetchHeaderNews()
            return
        }

        if (isFetchNeeded(Date(time), 1)){
            fetchHeaderNews()
            return
        }
    }

    private suspend fun fetchHeaderNews(){
        val country = Locale.getDefault().country

        val general = getArticleByCategoryAsync(ViewTypes.MAIN_ARTICLE, "business", country, "general")
        val business = getArticleByCategoryAsync(ViewTypes.BEIGE_ARTICLE, "technology", country, "business")
        val technology = getArticleByCategoryAsync(ViewTypes.BEIGE_ARTICLE, "science", country, "technology")
        val science = getArticleByCategoryAsync(ViewTypes.BEIGE_ARTICLE, "health", country, "science")
        val health = getArticleByCategoryAsync(ViewTypes.BEIGE_ARTICLE, "sports", country, "health")
        val sports = getArticleByCategoryAsync(ViewTypes.BEIGE_ARTICLE, null, country, "sports")

        val news = listOf(general.await(), business.await(), technology.await(),
            science.await(), health.await(), sports.await()).flatten()

        with(sharedPreferences.edit()){
            putLong(TIME_REQUEST, Date().time)
            commit()
        }

        if (news.isNotEmpty()) persistHeadNews(news)
    }


    private suspend fun getArticleByCategoryAsync(viewType:Int, groupeTitle:String?, country:String, category:String) = GlobalScope.async {
        val result = mutableListOf<Article>()
        val call = safeApiCall(Dispatchers.IO){
            apiServer.getHeadNews(country, category, 6)
        }
        call?.let {
            for (i in it.articles){
                i.category = "#$category"
                i.viewType = ViewTypes.ORDINARY_ARTICLE
            }
            it.articles[0].viewType = viewType
            result.addAll(it.articles)
        }
        result.add(Article(ViewTypes.TITLE_GROUP, groupeTitle, null, null, null, null, null, null, null, null))
        return@async result
    }

    private fun persistHeadNews(newsList:List<Article>){
        GlobalScope.launch {
            newsDao.deletrAll()
            newsDao.insert(newsList)
        }
    }

    private fun isFetchNeeded(requestTime:Date, wait:Int):Boolean{
        val after = Date(requestTime.time + wait * DateUtils.HOUR_IN_MILLIS)
        return Date(Date().time).after(after)
    }

}