package com.glowstudio.android.blindsjn.network

/**
 * Retrofit library 서버 통신 객체
 * - 서버 주소 설정
 * - OkHttp 로깅 인터셉터 적용 (디버깅 용도)
 */

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    // ✅ PHP 서버 주소 (변경 가능)
    private const val BASE_URL = "http://wonrdc.duckdns.org/"

    // ✅ OkHttp 로그 인터셉터 설정 (요청/응답 로그 확인)
    private val client by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    // ✅ Retrofit 객체 생성
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client) // 로그 인터셉터 적용
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // ✅ API 인터페이스 연결
    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
