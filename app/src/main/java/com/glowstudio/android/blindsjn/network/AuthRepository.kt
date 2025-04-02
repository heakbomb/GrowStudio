package com.glowstudio.android.blindsjn.network

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.glowstudio.android.blindsjn.model.ApiResponse
import com.glowstudio.android.blindsjn.model.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

object AuthRepository {

    /**
     * 로그인 요청 함수
     * - Retrofit을 사용해 로그인 API 호출
     * - 서버 응답 디버깅 로그 포함
     * - 예외 상황 처리 및 토스트 메시지 표시
     */
    suspend fun login(context: Context, phoneNumber: String, password: String): Boolean {
        if (!isNetworkAvailable(context)) {
            showToast(context, "인터넷 연결을 확인해주세요.")
            return false
        }

        return try {
            val request = LoginRequest(phoneNumber, password)
            val response = withContext(Dispatchers.IO) {
                InternalServer.api.login( request)
            }

            // ✅ 디버깅 로그 추가 (서버 응답 분석용)
            Log.d("LoginDebug", "response.isSuccessful: ${response.isSuccessful}")
            Log.d("LoginDebug", "response.code(): ${response.code()}")
            Log.d("LoginDebug", "response.body(): ${response.body()?.status}, ${response.body()?.message}, user_id = ${response.body()?.user_id}")
            Log.d("LoginDebug", "response.errorBody(): ${response.errorBody()?.string()}")

            // ✅ 서버 응답 처리
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.status == "success") {
                    showToast(context, "로그인 성공! 사용자 ID: ${body.user_id}")
                    return true
                } else {
                    showToast(context, body?.message ?: "로그인 실패: 알 수 없는 오류")
                }
            } else {
                showToast(context, "서버 응답 오류: ${response.code()}")
            }
            false

        } catch (e: IOException) {
            logError("네트워크 오류", e)
            showToast(context, "서버에 연결할 수 없습니다.")
            false

        } catch (e: HttpException) {
            logError("HTTP 오류", e)
            showToast(context, "서버 오류가 발생했습니다.")
            false

        } catch (e: Exception) {
            logError("알 수 없는 오류", e)
            showToast(context, "예상치 못한 오류가 발생했습니다.")
            false
        }
    }

    /**
     * 실제 네트워크 연결 확인 함수 필요 시 구현
     */
    private fun isNetworkAvailable(context: Context): Boolean {
        return true // 외부에서 isNetworkAvailable 구현 시 교체 가능
    }

    private fun logError(tag: String, e: Exception) {
        Log.e("AuthRepository", "$tag: ${e.message}", e)
    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
