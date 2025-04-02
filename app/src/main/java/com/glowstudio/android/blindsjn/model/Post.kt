package com.glowstudio.android.blindsjn.model

data class Post(
    val id: Int,
    val title: String,
    val content: String,
    val category: String,
    val experience: String,
    val time: String,
    val commentCount: Int,
    val likeCount: Int
)


// 게시글 목록 응답 모델

data class PostListResponse(
    val status: String,
    val message: String,
    val data: List<Post>
)

