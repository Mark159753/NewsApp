package com.example.newsapp.data.network

import com.example.newsapp.model.NewsResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://newsapi.org/v2/"
const val API_KEY = "7d7e7244b9cc4bb0af5ea5799618332e"

interface ApiService {

    @GET("top-headlines")
    suspend fun getHeadNews(
        @Query("country") countryCode:String,
        @Query("category") category:String,
        @Query("pageSize") pageSize:Int
    ):retrofit2.Response<NewsResponse>

    companion object{
        operator fun invoke():ApiService{
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY

            val apiKeyInterceptor = object :Interceptor{
                override fun intercept(chain: Interceptor.Chain): Response {
                    val mUrl = chain.request()
                        .url
                        .newBuilder()
                        .addQueryParameter("apiKey", API_KEY)
                        .build()
                    val request = chain.request()
                        .newBuilder()
                        .url(mUrl)
                        .build()
                    return chain.proceed(request)
                }
            }

            val httpClient = OkHttpClient.Builder()
                .addInterceptor(logger)
                .addInterceptor(apiKeyInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
                .create(ApiService::class.java)
        }
    }

}