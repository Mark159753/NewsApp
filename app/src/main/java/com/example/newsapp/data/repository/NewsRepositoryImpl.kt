package com.example.newsapp.data.repository

import android.view.View
import androidx.lifecycle.LiveData
import com.example.newsapp.data.db.dao.NewsDao
import com.example.newsapp.data.network.ApiService
import com.example.newsapp.data.network.response.safeApiCall
import com.example.newsapp.model.Article
import com.example.newsapp.model.NewsResponse
import com.example.newsapp.ui.ViewTypes
import kotlinx.coroutines.*
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val apiServer:ApiService,
    private val newsDao: NewsDao
): NewsRepository {


    override suspend fun getArticles(): LiveData<List<Article>> {
        return withContext(Dispatchers.IO){
            initHeadNews()
            return@withContext newsDao.getNews()
        }
    }


    private suspend fun initHeadNews(){
        // TODO Add Time
        fetchHeaderNews()
    }

    private suspend fun fetchHeaderNews(){
        val country = "ua"
        val news = mutableListOf<Article>()

        val general = getArticleByCategoryAsync(ViewTypes.MAIN_ARTICLE, "business", country, "general")
        val business = getArticleByCategoryAsync(ViewTypes.BEIGE_ARTICLE, "technology", country, "business")
        val technology = getArticleByCategoryAsync(ViewTypes.BEIGE_ARTICLE, "science", country, "technology")
        val science = getArticleByCategoryAsync(ViewTypes.BEIGE_ARTICLE, "health", country, "science")
        val health = getArticleByCategoryAsync(ViewTypes.BEIGE_ARTICLE, "sports", country, "health")
        val sports = getArticleByCategoryAsync(ViewTypes.BEIGE_ARTICLE, null, country, "sports")

        news.addAll(general.await())
        news.addAll(business.await())
        news.addAll(technology.await())
        news.addAll(science.await())
        news.addAll(health.await())
        news.addAll(sports.await())

        if (news.isNotEmpty()) persistHeadNews(news)
    }


    private suspend fun getArticleByCategoryAsync(viewType:Int, groupeTitle:String?, country:String, category:String) = GlobalScope.async {
        val result = mutableListOf<Article>()
        val call = safeApiCall(Dispatchers.IO){
            apiServer.getHeadNews(country, category, 6)
        }
        call?.let {
            for (i in it.articles){
                i.category = category
                i.viewType = ViewTypes.ORDINARY_ARTICLE
            }
            it.articles[0].viewType = viewType
            result.addAll(it.articles)
        }
        if (groupeTitle != null) result.add(Article(ViewTypes.TITLE_GROUP, groupeTitle, null, null, null, null, null, null, null, null))
        return@async result
    }

    private fun persistHeadNews(newsList:List<Article>){
        GlobalScope.launch {
            newsDao.deletrAll()
            newsDao.insert(newsList)
        }
    }

}