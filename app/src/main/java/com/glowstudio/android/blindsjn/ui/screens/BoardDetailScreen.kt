package com.glowstudio.android.blindsjn.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit

// 게시글 데이터 모델
// TODO: 서버 API의 JSON 응답 필드와 맞게 수정해야 함
data class Post(
    val title: String, // 게시글 제목
    val content: String, // 게시글 내용 미리보기
    val category: String, // 게시글 카테고리
    val time: String, // 게시글 작성 시간
    val commentCount: Int // 댓글 수
)

@Composable
fun BoardDetailScreen(navController: NavController, title: String) {
    // 현재 선택된 카테고리 상태
    // TODO: 서버에서 게시글을 가져올 때 이 값을 파라미터로 사용해야 함
    var selectedCategory by remember { mutableStateOf("모든 분야") }

    // 카테고리와 게시글 데이터
    // TODO: 아래는 더미 데이터. 서버 연동 시 API 호출로 대체 필요
    val categories = listOf("모든 분야", "카페", "식당", "배달 전문", "패스트푸드", "호텔")
    val posts = listOf(
        Post("비수기네요...", "요즘 장사가 잘 안 되네요.", "카페", "17분 전", 18),
        Post("오늘도 화이팅입니다.", "손님 줄어든 게 너무 힘드네요.", "식당", "57분 전", 3),
        Post("봉어빵 메뉴 잘 나가나요?", "겨울 한정 메뉴를 추가하려 합니다.", "카페", "11:27", 8),
        Post("배달 플랫폼 수수료 또 오른다네요;", "수수료 때문에 고민입니다.", "배달 전문", "8:29", 48)
    )

    Scaffold(
        floatingActionButton = {
            // 글쓰기 화면으로 이동
            FloatingActionButton(
                onClick = {
                    navController.navigate("writePost") // 글쓰기 화면으로 이동
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Edit, contentDescription = "글쓰기")
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // 카테고리 필터
                CategoryFilterRow(
                    categories = categories,
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it }
                )

                // 게시글 리스트
                // TODO: 서버 연동 시, 아래 필터링 로직 대신 서버 쿼리에서 처리하는 방식 검토
                val filteredPosts = posts.filter { post ->
                    selectedCategory == "모든 분야" || post.category.contains(selectedCategory)
                }
                PostList(posts = filteredPosts)
            }
        }
    )
}

@Composable
fun CategoryFilterRow(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    // 가로 스크롤 가능한 카테고리 필터
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            FilterChip(
                text = category,
                isSelected = category == selectedCategory,
                onClick = { onCategorySelected(category) }
            )
        }
    }
}

@Composable
fun FilterChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    // 선택된 카테고리를 강조하는 버튼
    Box(
        modifier = Modifier
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.small
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun PostList(posts: List<Post>) {
    // 게시글 리스트
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        items(posts) { post ->
            PostItem(post = post)
        }
    }
}

@Composable
fun PostItem(post: Post) {
    // 게시글 카드
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium)
            .clickable { /* TODO: 게시글 상세 이동 API 호출 */ }
            .padding(16.dp)
    ) {
        Text(text = post.title, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = post.content,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 2
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = post.category,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "${post.time}   댓글 ${post.commentCount}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
