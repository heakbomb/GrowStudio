package com.glowstudio.android.blindsjn.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.border
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class BoardCategory(
    val emoji: String,
    val title: String,
    val postCount: String
)

// TODO: 'n개의 새 게시물'은 실제 값이 들어가야 함
val boardCategories = listOf(
    // 아이콘은 이모지를 사용함
    BoardCategory("🍴", "음식점 및 카페", "298개의 새 게시물"),
    BoardCategory("🛍️", "쇼핑 및 리테일", "128개의 새 게시물"),
    BoardCategory("💊", "건강 및 의료", "57개의 새 게시물"),
    BoardCategory("🏨", "숙박 및 여행", "298개의 새 게시물"),
    BoardCategory("📚", "교육 및 학습", "36개의 새 게시물"),
    BoardCategory("🎮", "여가 및 오락", "98개의 새 게시물"),
    BoardCategory("💰", "금융 및 공공기관", "20개의 새 게시물")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoardScreen(navController: NavController? = null) {
    Scaffold(
        content = { paddingValues ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // 한 줄에 2개씩 배치
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(8.dp), // 전체 여백
                verticalArrangement = Arrangement.spacedBy(4.dp), // 아이템 간 세로 간격
                horizontalArrangement = Arrangement.spacedBy(4.dp) // 아이템 간 가로 간격
            ) {
                items(boardCategories) { category ->
                    BoardCategoryItem(
                        category = category,
                        onClick = {
                            navController?.navigate("boardDetail/${category.title}")
                        }
                    )
                }
            }
        }
    )
}

@Composable
fun BoardCategoryItem(category: BoardCategory, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(4.dp) // 아이템 외부 여백
            .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f), MaterialTheme.shapes.medium) // 테두리
            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium) // 배경색과 모서리 둥글기
            .padding(8.dp), // 아이템 내부 여백
        verticalAlignment = Alignment.CenterVertically // 아이콘과 텍스트를 수직 중앙 정렬
    ) {
        // 아이콘 배경
        Box(
            modifier = Modifier
                .size(48.dp) // 아이콘 배경 크기
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), MaterialTheme.shapes.medium), // 아이콘 배경색과 둥근 모서리
            contentAlignment = Alignment.Center
        ) {
            // 이모지 또는 아이콘
            Text(
                text = category.emoji,
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(modifier = Modifier.width(12.dp)) // 아이콘과 텍스트 사이 간격

        // 제목과 게시물 수
        Column {
            Text( // 게시판 이름
                text = category.title,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text( // 새 게시물 수
                text = category.postCount,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
