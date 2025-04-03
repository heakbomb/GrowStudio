package net.flow9.kotlin.myapplication.model

import com.google.gson.annotations.SerializedName

data class BusinessStatusResponse(
    @SerializedName("status") val status: String,        // 요청 상태
    @SerializedName("data") val data: List<BusinessData> // 응답 데이터
)

data class BusinessData(
    @SerializedName("b_no") val businessNumber: String,
    @SerializedName("b_stt") val businessStatus: String,
    @SerializedName("b_stt_cd") val businessStatusCode: String,
    @SerializedName("tax_type") val taxType: String,
    @SerializedName("tax_type_cd") val taxTypeCode: String,
    @SerializedName("end_dt") val endDate: String?,
    @SerializedName("utcc_yn") val utccYn: String,
    @SerializedName("tax_type_change_dt") val taxTypeChangeDate: String?,
    @SerializedName("invoice_apply_dt") val invoiceApplyDate: String?
)