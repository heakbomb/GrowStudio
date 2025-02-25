package com.glowstudio.android.blindsjn.model

data class Post(
    val id: Int, // 게시글 ID 추가
    val title: String, // 게시글 제목
    val content: String, // 게시글 내용
    val category: String, // 게시글 카테고리
    val experience: String, // 작성자 경력
    val time: String, // 게시글 작성 시간
    val commentCount: Int, // 댓글 수
    val likeCount: Int // 좋아요 수
)

// 다른 스크립트에서 import하여 사용
