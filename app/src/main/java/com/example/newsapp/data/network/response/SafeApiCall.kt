package com.example.newsapp.data.network.response

import android.util.Log
import com.example.newsapp.model.NewsResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception

@Suppress("UNCHECKED_CAST")
suspend fun <T> safeApiCall(dispatcher:CoroutineDispatcher, apiCall: suspend () -> Response<T>): T? {
    return withContext(dispatcher){
        try {
            val response = apiCall.invoke()
            if (response.isSuccessful){
                return@withContext response.body() as T
            }else{
                Log.e("Response Code", response.code().toString())
                return@withContext null
            }
        }catch (e:Exception){
            Log.e("Connectivity Error", e.message)
            return@withContext null
        }
    }
}