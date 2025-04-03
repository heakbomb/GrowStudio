package com.glowstudio.android.blindsjn.model

data class ApiResponse(
    val status: String,
    val message: String,
    val user_id: Int? = null
)
