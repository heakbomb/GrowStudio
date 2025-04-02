package com.glowstudio.android.blindsjn.repository

import com.glowstudio.android.blindsjn.model.BasicResponse
import com.glowstudio.android.blindsjn.model.PostListResponse
import com.glowstudio.android.blindsjn.network.*
import retrofit2.Response

object PostRepository {

    suspend fun savePost(request: PostRequest): Response<BasicResponse> {
        return RetrofitInstance.api.savePost(request)
    }

    suspend fun loadPosts(): Response<PostListResponse> {
        return RetrofitInstance.api.getAllPosts()
    }

    suspend fun editPost(request: EditPostRequest): Response<BasicResponse> {
        return RetrofitInstance.api.editPost(request)
    }

    suspend fun deletePost(request: DeleteRequest): Response<BasicResponse> {
        return RetrofitInstance.api.deletePost(request)
    }

    suspend fun saveComment(request: CommentRequest): Response<BasicResponse> {
        return RetrofitInstance.api.saveComment(request)
    }

    suspend fun editComment(request: EditCommentRequest): Response<BasicResponse> {
        return RetrofitInstance.api.editComment(request)
    }

    suspend fun deleteComment(request: DeleteCommentRequest): Response<BasicResponse> {
        return RetrofitInstance.api.deleteComment(request)
    }
}
