package com.glowstudio.android.blindsjn.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glowstudio.android.blindsjn.network.NewsArticle
import com.glowstudio.android.blindsjn.network.NewsResponse
import com.glowstudio.android.blindsjn.network.NewsRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import retrofit2.Response

class NewsViewModel : ViewModel() {
    var articles = mutableStateOf<List<NewsArticle>>(emptyList())
        private set

    var isLoading = mutableStateOf(true)
        private set

    var errorMessage = mutableStateOf("")
        private set

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch {
            try {
                val today = LocalDate.now().minusDays(2)
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val formattedDate = today.format(formatter)

                val response: Response<NewsResponse> = NewsRepository().fetchEverything(
                    query = "Apple",
                    from = formattedDate, // 오늘 날짜로 자동 설정
                    sortBy = "popularity",
                    apiKey = "6c4ce8bc6bbb4379aa2c94db1e3fa6f7"
                )
                if (response.isSuccessful) {
                    articles.value = response.body()?.articles ?: emptyList()
                    Log.d("NewsViewModel", "API 호출 성공: ${response.code()} / ${response.message()}")
                    val articlesList = response.body()?.articles
                    Log.d("NewsViewModel", "응답 받은 기사 수: ${articlesList?.size}")
                } else {
                    errorMessage.value = "API 호출 실패: ${response.message()}"
                    Log.e("NewsViewModel", "API 호출 실패: ${response.code()} / ${response.message()}")
                }
            } catch (e: Exception) {
                errorMessage.value = "에러 발생: ${e.localizedMessage}"
                Log.e("NewsViewModel", "Exception: ${e.localizedMessage}", e)
            } finally {
                isLoading.value = false
            }
        }
    }
}