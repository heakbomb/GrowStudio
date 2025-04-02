package com.glowstudio.android.blindsjn.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.*
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.glowstudio.android.blindsjn.network.NewsArticle
import com.glowstudio.android.blindsjn.ui.NewsViewModel
import coil.compose.AsyncImage

@Composable
fun HomeScreen() {
    val viewModel: NewsViewModel = viewModel()
    val articles by viewModel.articles
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage

    LaunchedEffect(Unit) {
        // 이미 ViewModel에서 init { fetchNews() }로 불러오고 있어서 생략 가능
        // viewModel.fetchNews()
    }

    when {
        isLoading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        errorMessage.isNotEmpty() -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = errorMessage, color = Color.Red)
            }
        }
        else -> {
            LazyColumn {
                items(articles) { article ->
                    NewsItem(article)
                }
            }
        }
    }
}

@Composable
fun NewsItem(article: NewsArticle) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = article.title ?: "제목 없음", style = MaterialTheme.typography.titleMedium)
        article.urlToImage?.let {
            AsyncImage(model = it, contentDescription = null)
        }
        Text(text = article.description ?: "설명 없음", style = MaterialTheme.typography.bodyMedium)
        Text(text = "작성자: ${article.author ?: "알 수 없음"}")
        Text(text = article.publishedAt ?: "", style = MaterialTheme.typography.labelSmall)
        Divider(modifier = Modifier.padding(vertical = 8.dp))
    }
}