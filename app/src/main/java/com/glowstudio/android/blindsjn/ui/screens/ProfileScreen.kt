package com.glowstudio.android.blindsjn.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.glowstudio.android.blindsjn.network.AutoLoginManager
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    onLogoutClick: () -> Unit  // 네비게이션 이동
    ) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var showLogoutPopup by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "프로필")

        // 로그아웃 버튼 (팝업만 띄우기)
        Button(
            onClick = {
                showLogoutPopup = true  // 팝업만 띄움
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 0.dp, y = 50.dp)
                .padding(8.dp)
                .height(50.dp)
                .wrapContentWidth()
        ) {
            Text(text = "로그아웃")
        }

        // 로그아웃 팝업
        if (showLogoutPopup) {
            AlertDialog(
                onDismissRequest = { showLogoutPopup = false },  // 팝업 외부 클릭 시 닫기
                title = { Text("로그아웃") },
                text = { Text("로그아웃하시겠습니까?") },

                // 확인 버튼 → 자동 로그인 정보 삭제 + 화면 전환
                confirmButton = {
                    TextButton(onClick = {
                        coroutineScope.launch {
                            try {
                                // 자동 로그인 정보 삭제
                                AutoLoginManager.clearLoginInfo(context)

                                showLogoutPopup = false
                                onLogoutClick()

                            } catch (e: Exception) {
                                Log.e("HomeScreen", "로그아웃 실패: ${e.message}")
                            }
                        }
                    }) {
                        Text("확인")
                    }
                },

                // 취소 버튼 → 팝업만 닫기
                dismissButton = {
                    TextButton(onClick = { showLogoutPopup = false }) {
                        Text("취소")
                    }
                }
            )
        }
    }
}