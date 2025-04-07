/*
* 뉴스 세부 기사 확인 스크린
*
*
* */


package com.glowstudio.android.blindsjn.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun NewsDetailScreen(title: String, content: String?, description: String?, imageUrl: String?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (!imageUrl.isNullOrBlank()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "기사 이미지",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = if (!content.isNullOrBlank()) content else description ?: "내용이 없습니다.",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}