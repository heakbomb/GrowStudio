package com.glowstudio.android.blindsjn.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.glowstudio.android.blindsjn.model.BasicResponse
import com.glowstudio.android.blindsjn.model.CommentListResponse
import com.glowstudio.android.blindsjn.model.CommentRequest
import com.glowstudio.android.blindsjn.model.DeleteCommentRequest
import com.glowstudio.android.blindsjn.model.EditCommentRequest
import com.glowstudio.android.blindsjn.model.PostListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Query
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.DELETE

// ✅ 요청 데이터 클래스
data class SignupRequest(val phoneNumber: String, val password: String)
data class LoginRequest(val phoneNumber: String, val password: String)
data class PostRequest(val title: String, val content: String, val user_id: Int, val industry: String)
data class EditPostRequest(val id: Int, val title: String, val content: String)
data class DeleteRequest(val id: Int)

// ✅ 공통 응답 데이터 클래스
data class ApiResponse(val status: String, val message: String)

// ✅ 네트워크 상태 확인 함수
fun isNetworkAvailable(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = cm.activeNetwork ?: return false
    val capabilities = cm.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

// ✅ Retrofit API 인터페이스
interface ApiService {

    // 회원가입 요청
    @POST("signup.php")
    suspend fun signup(@Body request: SignupRequest): Response<ApiResponse>

    // 로그인 요청
    @POST("login.php")
    suspend fun login(@Body request: LoginRequest): Response<ApiResponse>

    // 게시글 저장
    @POST("Save_post.php")
    suspend fun savePost(@Body request: PostRequest): Response<ApiResponse>

    // 게시글 수정
    @PUT("Edit_post.php")
    suspend fun editPost(@Body request: EditPostRequest): Response<ApiResponse>

    // 게시글 삭제
    @DELETE("Delete_post.php")
    suspend fun deletePost(@Body request: DeleteRequest): Response<ApiResponse>

    // 게시글 전체 불러오기
    @GET("Load_post.php")
    suspend fun getAllPosts(): Response<PostListResponse>

    @GET("Popular_posts.php")
    suspend fun getPopularPosts(): Response<PostListResponse>

    @GET("Load_comment.php")
    suspend fun getComments(@Query("post_id") postId: Int): Response<CommentListResponse>

    @POST("Save_comment.php")
    suspend fun saveComment(@Body request: CommentRequest): Response<BasicResponse>

    @DELETE("Delete_comment.php")
    suspend fun deleteComment(@Body request: DeleteCommentRequest): Response<BasicResponse>

    @PUT("Edit_comment.php")
    suspend fun editComment(@Body request: EditCommentRequest): Response<BasicResponse>

}
