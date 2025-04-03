package net.flow9.kotlin.myapplication.network

import net.flow9.kotlin.myapplication.model.BusinessRequest
import net.flow9.kotlin.myapplication.model.BusinessStatusResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface BusinessStatusService {
    @POST("status")
    fun getBusinessStatus(
        @Query("serviceKey") serviceKey: String, // Query Parameter로 서비스 키 전달
        @Body body: BusinessRequest              // 요청 Body
    ): Call<BusinessStatusResponse>
}
