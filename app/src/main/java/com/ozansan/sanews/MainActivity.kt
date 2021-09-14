package com.ozansan.sanews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ozansan.sanews.data.models.News
import com.ozansan.sanews.main.MainViewModel
import com.ozansan.sanews.ui.theme.SanewsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getAllStories()
        setContent {
            val topStoriesState = viewModel.topStories.collectAsState()
            val isRefreshing by viewModel.isRefreshing.collectAsState()
            SanewsTheme {
                // A surface container using the 'background' color from the theme
                Scaffold() {
                    
                }
                when (topStoriesState.value) {
                    MainViewModel.NewsEvent.Empty -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(32.dp),
                                color = Color.Gray
                            )
                        }
                    }
                    is MainViewModel.NewsEvent.Failure -> {
                        Text(
                            (topStoriesState.value as MainViewModel.NewsEvent.Failure).errorText,
                            color = Color.Red
                        )
                    }
                    MainViewModel.NewsEvent.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(32.dp),
                                color = Color.Gray
                            )
                        }
                    }
                    is MainViewModel.NewsEvent.Success -> {
                        SwipeRefresh(
                            state = rememberSwipeRefreshState(isRefreshing),
                            onRefresh = { viewModel.getAllStories() }
                        ) {
                            TopStoriesList((topStoriesState.value as MainViewModel.NewsEvent.Success).results)
                        }
                    }
                }


            }
        }
    }
}

@Composable
fun TopStoriesList(stories: List<News>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(stories) { _, story ->
            NewsItem(story)
        }
    }
}