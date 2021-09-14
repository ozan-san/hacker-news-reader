package com.ozansan.sanews.data

import com.ozansan.sanews.data.models.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsApi {

    @GET("topstories.json")
    suspend fun getTopStories() : Response<List<Int>>

    @GET("item/{id}.json")
    suspend fun getItem(@Path("id") id: String): Response<News>

}