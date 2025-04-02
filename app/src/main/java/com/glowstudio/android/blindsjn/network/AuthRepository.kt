package com.glowstudio.android.blindsjn.network

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.glowstudio.android.blindsjn.network.LoginRequest
import com.glowstudio.android.blindsjn.network.InternalServer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

object AuthRepository {

    /**
     * 로그인 요청 함수
     * - 서버와 통신하여 로그인 결과를 반환합니다.
     * - 네트워크 상태를 확인하고 예외 상황을 처리합니다.
     *
     * @param context Context: 앱의 컨텍스트
     * @param phoneNumber String: 사용자 전화번호
     * @param password String: 사용자 비밀번호
     * @return Boolean: 로그인 성공 여부
     */
    suspend fun login(context: Context, phoneNumber: String, password: String): Boolean {
        // 네트워크 연결 확인
        if (!isNetworkAvailable(context)) {
            showToast(context, "인터넷 연결을 확인해주세요.")
            return false
        }

        return try {
            // 서버에 로그인 요청
            val request = LoginRequest(phoneNumber, password)
            val response = withContext(Dispatchers.IO) {
                InternalServer.api.login(request)
            }

            // 성공적인 응답 확인
            if (response.isSuccessful && response.body()?.status == "success") {
                true
            } else {
                showToast(context, "로그인에 실패했습니다.")
                false
            }
        } catch (e: IOException) {
            // 네트워크 오류 처리
            logError("네트워크 오류", e)
            showToast(context, "서버에 연결할 수 없습니다.")
            false
        } catch (e: HttpException) {
            // HTTP 오류 처리
            logError("HTTP 오류", e)
            showToast(context, "서버 오류가 발생했습니다.")
            false
        } catch (e: Exception) {
            // 알 수 없는 오류 처리
            logError("알 수 없는 오류", e)
            showToast(context, "예상치 못한 오류가 발생했습니다.")
            false
        }
    }

    /**
     * 네트워크 상태 확인 함수
     * - 네트워크 연결 여부를 확인합니다.
     * @param context Context: 앱의 컨텍스트
     * @return Boolean: 네트워크 연결 여부
     */
    private fun isNetworkAvailable(context: Context): Boolean {
        // 실제 구현은 네트워크 상태를 체크하는 코드로 대체 필요
        return true
    }

    /**
     * 로그 메시지 기록 함수
     * - 오류 메시지를 기록합니다.
     * @param tag String: 로그 태그
     * @param e Exception: 예외 객체
     */
    private fun logError(tag: String, e: Exception) {
        Log.e("AuthRepository", "$tag: ${e.message}")
    }

    /**
     * 사용자에게 메시지 표시 함수
     * - 토스트 메시지를 표시합니다.
     * @param context Context: 앱의 컨텍스트
     * @param message String: 표시할 메시지
     */
    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}