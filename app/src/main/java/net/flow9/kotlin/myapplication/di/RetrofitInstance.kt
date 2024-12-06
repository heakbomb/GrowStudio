package net.flow9.kotlin.myapplication.di

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import net.flow9.kotlin.myapplication.network.BusinessStatusService

object RetrofitInstance {
    private const val BASE_URL = "https://api.odcloud.kr/api/nts-businessman/v1/"

    val api: BusinessStatusService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BusinessStatusService::class.java)
    }
}