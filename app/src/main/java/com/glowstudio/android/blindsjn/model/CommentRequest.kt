package com.glowstudio.android.blindsjn.model

data class CommentRequest(
    val post_id: Int,
    val user_id: Int,
    val content: String
)
