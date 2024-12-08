package com.glowstudio.android.blindsjn.ui.screens

/**
 * 로그인 스크린 로직
 *
 *
 **/

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.glowstudio.android.blindsjn.network.LoginRequest
import com.glowstudio.android.blindsjn.network.RetrofitInstance
import com.glowstudio.android.blindsjn.R

import kotlinx.coroutines.launch
import android.util.Log

suspend fun login(phoneNumber: String, password: String): Boolean {
    val request = LoginRequest(phoneNumber, password)
    val response = RetrofitInstance.api.login(request)

    //서버 통신 연결 확인 디버깅 코드
//    Log.d("LoginScreen", "Sending Request Data: $request")
//    Log.d("LoginScreen", "Response code: ${response.code()}")
//    Log.d("LoginScreen", "Response body: ${response.body()}")

    return if (response.isSuccessful) {
        val result = response.body()
        Log.d("LoginScreen", "Login result: $result")
        result?.status == "success"
    } else {
        Log.e("LoginScreen", "Error: ${response.errorBody()?.string()}")
        false
    }
}

@Composable
fun LoginScreen(
    onLoginClick: (Boolean) -> Unit,
    onSignupClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    //로그인 로직 변수
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    //팝업 스크린 변수
    var showEmptyFieldsPopup by remember { mutableStateOf(false) }
    var showInvalidCredentialsPopup by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 상단 이미지 영역
        Image(
            painter = painterResource(id = R.drawable.login_image), // drawable 폴더 확인
            contentDescription = "Login Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp) // 원하는 높이로 설정
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 입력 필드 및 버튼 영역
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 전화번호 입력 필드
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it.filter { char -> char.isDigit() } },
                label = { Text("전화번호") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 비밀번호 입력 필드
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("비밀번호") },
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        if (passwordVisible) {
                            Icon(
                                imageVector = Icons.Filled.Visibility,
                                contentDescription = "Hide password"
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Filled.VisibilityOff,
                                contentDescription = "Show password"
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 로그인 버튼
            // 로그인 버튼 눌렀을 때 서버랑 통신 후 인증
            Button(
                onClick = {
                    coroutineScope.launch {
                        if (phoneNumber.isEmpty() || password.isEmpty()) {
                            showEmptyFieldsPopup = true
                        } else {
                            try {
                                val success = login(phoneNumber, password)
                                if (success) {
                                    onLoginClick(true) // 홈 화면으로 이동
                                } else {
                                    showInvalidCredentialsPopup = true
                                }
                            } catch (e: Exception) {
                                Log.e("LoginScreen", "Login error: ${e.message}", e)
                                showInvalidCredentialsPopup = true
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("로그인")
            }

            // 팝업 표시
            // 텍스트필드가 비었을 때 출력
            if (showEmptyFieldsPopup) {
                AlertDialog(
                    onDismissRequest = { showEmptyFieldsPopup = false },
                    title = { Text("입력 오류") },
                    text = { Text("전화번호와 비밀번호를 입력해주세요.") },
                    confirmButton = {
                        TextButton(onClick = { showEmptyFieldsPopup = false }) {
                            Text("확인")
                        }
                    }
                )
            }
            // 계정정보가 서버와 옳지 않을 때 출력
            if (showInvalidCredentialsPopup) {
                AlertDialog(
                    onDismissRequest = { showInvalidCredentialsPopup = false },
                    title = { Text("로그인 실패") },
                    text = { Text("전화번호 또는 비밀번호가 올바르지 않습니다.") },
                    confirmButton = {
                        TextButton(onClick = { showInvalidCredentialsPopup = false }) {
                            Text("확인")
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 회원가입 버튼
            TextButton(onClick = onSignupClick) {
                Text("회원가입")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 비밀번호 찾기 버튼
            TextButton(onClick = onForgotPasswordClick) {
                Text("비밀번호 찾기")
            }
        }
    }
}