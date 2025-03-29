package com.glowstudio.android.blindsjn.repository

import com.glowstudio.android.blindsjn.model.*
import com.glowstudio.android.blindsjn.network.*
import retrofit2.Response

object PostRepository {

    // ✅ 게시글 저장
    suspend fun savePost(title: String, content: String, userId: Int, industry: String): Response<ApiResponse> {
        val request = PostRequest(title, content, userId, industry)
        return RetrofitInstance.api.savePost(request)
    }

    // ✅ 게시글 전체 불러오기
    suspend fun loadPosts(): Response<PostListResponse> {
        return RetrofitInstance.api.getAllPosts()
    }

    // ✅ 게시글 수정
    suspend fun editPost(postId: Int, title: String, content: String): Response<ApiResponse> {
        val request = EditPostRequest(postId, title, content)
        return RetrofitInstance.api.editPost(request)
    }

    // ✅ 게시글 삭제
    suspend fun deletePost(postId: Int): Response<ApiResponse> {
        val request = DeleteRequest(postId)
        return RetrofitInstance.api.deletePost(request)
    }

    // ✅ 인기 게시글 불러오기
    suspend fun loadPopularPosts(): Response<PostListResponse> {
        return RetrofitInstance.api.getPopularPosts()
    }

    // ✅ 댓글 불러오기
    suspend fun loadComments(postId: Int): Response<CommentListResponse> {
        return RetrofitInstance.api.getComments(postId)
    }

    // ✅ 댓글 저장
    suspend fun saveComment(postId: Int, userId: Int, content: String): Response<BasicResponse> {
        val request = CommentRequest(postId, userId, content)
        return RetrofitInstance.api.saveComment(request)
    }

    // ✅ 댓글 삭제
    suspend fun deleteComment(commentId: Int): Response<BasicResponse> {
        val request = DeleteCommentRequest(commentId)
        return RetrofitInstance.api.deleteComment(request)
    }

    // ✅ 댓글 수정
    suspend fun editComment(commentId: Int, content: String): Response<BasicResponse> {
        val request = EditCommentRequest(commentId, content)
        return RetrofitInstance.api.editComment(request)
    }
}
