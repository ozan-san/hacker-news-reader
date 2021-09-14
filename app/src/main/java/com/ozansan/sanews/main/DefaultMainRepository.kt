package com.ozansan.sanews.main

import com.ozansan.sanews.data.NewsApi
import com.ozansan.sanews.data.models.News
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val api: NewsApi
) : MainRepository {
    override suspend fun getTopStoryIDs(): Response<List<Int>> {
        return api.getTopStories()
    }

    override suspend fun getStory(storyId: Int): Response<News> {
        return api.getItem(storyId.toString())
    }
}