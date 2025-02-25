package com.glowstudio.android.blindsjn.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.glowstudio.android.blindsjn.model.Post


@Composable
fun PostDetailScreen(navController: NavController, postId: String) {
    var post by remember { mutableStateOf<Post?>(null) }
    var comments by remember { mutableStateOf<List<Comment>>(emptyList()) }
    var newComment by remember { mutableStateOf("") }
    var isLiked by remember { mutableStateOf(false) }
    var likeCount by remember { mutableStateOf(0) }

    // 더미 데이터 (실제 앱에서는 서버에서 postId로 데이터를 불러와야 함)
    LaunchedEffect(postId) {
        post = fetchPostById(postId)
        println("PostDetailScreen - Loaded post: ${post?.title}") // 디버깅 로그 확인용
    }

    Scaffold(
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                post?.let {
                    Text(text = it.title, style = MaterialTheme.typography.headlineMedium)
                    Text(text = it.content, style = MaterialTheme.typography.bodyMedium)

                    // 좋아요 버튼
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {
                                isLiked = !isLiked
                                likeCount += if (isLiked) 1 else -1
                            }
                        ) {
                            Icon(
                                imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = "좋아요"
                            )
                            Text("$likeCount")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 댓글 리스트
                    LazyColumn {
                        items(comments) { comment ->
                            CommentItem(comment)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 댓글 입력
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = newComment,
                            onValueChange = { newComment = it },
                            modifier = Modifier.weight(1f),
                            placeholder = { Text("댓글을 입력하세요...") }
                        )
                        Button(onClick = {
                            if (newComment.isNotBlank()) {
                                comments = comments + Comment("익명", newComment)
                                newComment = ""
                            }
                        }) {
                            Text("등록")
                        }
                    }
                } ?: Text("게시글을 불러오는 중...")
            }
        }
    )
}

// 댓글 아이템
@Composable
fun CommentItem(comment: Comment) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(text = comment.username, style = MaterialTheme.typography.bodyMedium)
        Text(text = comment.content, style = MaterialTheme.typography.bodySmall)
    }
}

// 더미 데이터
data class Comment(val username: String, val content: String)

// 게시글과 댓글 불러오는 함수
fun fetchPostById(postId: String): Post {
    val dummyPosts = listOf(
        Post(1, "비수기네요...", "요즘 장사가 잘 안 되네요.", "카페", "/2년", "17분 전", 18, 10),
        Post(2, "오늘도 화이팅입니다.", "손님 줄어든 게 너무 힘드네요.", "식당", "/1년", "57분 전", 3, 5)
    )

    println("fetchPostById - Looking for postId: $postId") // 로그 확인용

    return dummyPosts.find { it.id.toString() == postId }
        ?: Post(0, "게시글 없음", "내용 없음", "없음", "0년", "방금 전", 0, 0)
}


fun fetchCommentsByPostId(postId: String): List<Comment> {
    return listOf(
        Comment("익명", "좋은 글 감사합니다!"),
        Comment("익명", "내용이 너무 공감돼요.")
    )
}
