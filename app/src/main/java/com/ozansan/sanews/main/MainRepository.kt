package com.ozansan.sanews.main

import com.ozansan.sanews.data.models.News
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Response

interface MainRepository {

    suspend fun getTopStoryIDs(): Response<List<Int>>

    suspend fun getStory(storyId: Int): Response<News>
}