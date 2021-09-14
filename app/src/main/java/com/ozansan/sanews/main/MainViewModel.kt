package com.ozansan.sanews.main

import androidx.lifecycle.ViewModel
import com.ozansan.sanews.data.models.News
import com.ozansan.sanews.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage ?: "UNKNOWN"}")
    }

    sealed class NewsEvent {
        class Success(val results: List<News>): NewsEvent()
        class Failure(val errorText: String): NewsEvent()
        object Loading : NewsEvent()
        object Empty: NewsEvent()
    }

    private fun onError(message: String) {
        _topStories.value = NewsEvent.Failure(message)
    }

    private val _topStories = MutableStateFlow<NewsEvent>(NewsEvent.Empty)
    val topStories: StateFlow<NewsEvent> = _topStories

    private val _isRefreshing = MutableStateFlow<Boolean>(true)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    fun getAllStories() {
            _isRefreshing.value = true
            job = CoroutineScope(dispatchers.io + exceptionHandler).launch {
                val response = repository.getTopStoryIDs()
                withContext(dispatchers.main) {
                    if (response.isSuccessful) {
                        withContext(dispatchers.io) {
                            if (response.body() != null) {
                                val newsList = mutableListOf<News>()
                                val top20items = response.body()!!.take(20)
                                for (itemId in top20items) {
                                    withContext(dispatchers.io) {
                                        val newsResponse = repository.getStory(itemId)
                                        if (newsResponse.isSuccessful && newsResponse.body() != null) {
                                            newsList.add(newsResponse.body()!!)
                                        }
                                    }
                                }
                                _topStories.value = NewsEvent.Success(newsList)
                                _isRefreshing.value = false
                            } else {
                                _topStories.value = NewsEvent.Failure("Null body returned!")
                                _isRefreshing.value = false
                            }

                        }
                    } else {
                        _topStories.value = NewsEvent.Failure(response.message())
                        _isRefreshing.value = false
                    }
                }
            }
    }
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}