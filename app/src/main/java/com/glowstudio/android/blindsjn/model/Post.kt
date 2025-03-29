package com.glowstudio.android.blindsjn.model

// 게시글 데이터 모델
data class Post(
    val id: Int,
    val title: String,
    val content: String,
    val category: String,
    val experience: String,      // ✅ 작성자 경력
    val time: String,            // ✅ 작성 시간
    val commentCount: Int,       // ✅ 댓글 수
    val likeCount: Int           // ✅ 좋아요 수
)


// 게시글 목록 응답 모델
data class PostListResponse(
    val status: String,
    val message: String,
    val data: List<Post> //
)


