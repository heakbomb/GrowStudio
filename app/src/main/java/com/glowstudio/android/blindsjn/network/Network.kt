package com.glowstudio.android.blindsjn.network

/**
 * PHP 통신 로직
 *
 * TODO: 추가적인 php 사용 필요시 작성
 **/

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

// 요청(발신) 데이터 클래스 정의
data class SignupRequest(val phoneNumber: String, val password: String)
data class LoginRequest(val phoneNumber: String, val password: String)
data class SignupResponse(val status: String, val message: String)

// 응답(수신) 데이터 클래스 정의
data class ApiResponse(val status: String, val message: String)

// Api 서비스 호출 인터페이스
interface ApiService {
    @POST("signup.php")
    suspend fun signup(@Body request: SignupRequest): Response<ApiResponse>

    @POST("login.php")
    suspend fun login(@Body request: LoginRequest): Response<ApiResponse>
}