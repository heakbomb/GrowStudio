package com.glowstudio.android.blindsjn.network

/**
 * Retrofit library 서버 통신 객체
 *
 *
 **/

import com.glowstudio.android.blindsjn.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://wonrdc.duckdns.org/" // PHP 서버 주소

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
// 공공 API 서버용 Retrofit 인스턴스
object PublicApiRetrofitInstance {
    private const val BASE_URL = "https://api.odcloud.kr/api/" // 공공 API URL

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: BusinessApiService by lazy {
        retrofit.create(BusinessApiService::class.java)
    }
}