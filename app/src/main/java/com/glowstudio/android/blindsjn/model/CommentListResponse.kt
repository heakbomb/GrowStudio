package com.glowstudio.android.blindsjn.model

data class Comment(
    val id: Int,
    val post_id: Int,
    val user_id: Int,
    val content: String,
    val created_at: String
)


data class CommentListResponse(
    val status: String,
    val message: String,
    val data: List<Comment>
)