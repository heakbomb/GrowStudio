/**
 * 로그인 스크린 로직
 *
 * TODO: 자동로그인 체크 박스
 **/
package com.glowstudio.android.blindsjn.ui.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.glowstudio.android.blindsjn.network.LoginRequest
import com.glowstudio.android.blindsjn.network.RetrofitInstance
import com.glowstudio.android.blindsjn.network.AuthRepository
import com.glowstudio.android.blindsjn.R
import com.glowstudio.android.blindsjn.network.AutoLoginManager
import com.glowstudio.android.blindsjn.network.isNetworkAvailable
import kotlinx.coroutines.launch
import java.io.IOException

// 로그인 함수 (서버 통신)
suspend fun login(phoneNumber: String, password: String): Boolean {
    val request = LoginRequest(phoneNumber, password)
    val response = RetrofitInstance.api.login(request)

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
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // 상태 변수
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var autoLoginEnabled by remember { mutableStateOf(false) }
    var showEmptyFieldsPopup by remember { mutableStateOf(false) }
    var showInvalidCredentialsPopup by remember { mutableStateOf(false) }
    var showNetworkErrorPopup by remember { mutableStateOf(false) } // 네트워크 오류 팝업 상태

    // 네트워크 상태 확인
    LaunchedEffect(Unit) {
        if (!isNetworkAvailable(context)) {
            showNetworkErrorPopup = true // 네트워크 오류 팝업 활성화
        } else {
            // 자동 로그인 로직
            autoLoginEnabled = AutoLoginManager.isAutoLoginEnabled(context)
            if (autoLoginEnabled) {
                AutoLoginManager.getSavedCredentials(context)?.let { (savedPhone, savedPassword) ->
                    phoneNumber = savedPhone
                    password = savedPassword
                    coroutineScope.launch {
                        val success = login(savedPhone, savedPassword)
                        if (success) onLoginClick(true)
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 네트워크 오류 팝업
        if (showNetworkErrorPopup) {
            AlertDialog(
                onDismissRequest = { /* 사용자가 팝업을 닫아도 무시 */ },
                title = { Text("네트워크 오류") },
                text = { Text("인터넷 연결이 필요합니다. 연결 상태를 확인해주세요.") },
                confirmButton = {
                    TextButton(onClick = {
                        showNetworkErrorPopup = false
                    }) {
                        Text("확인")
                    }
                }
            )
        }

        // 상단 이미지
        Image(
            painter = painterResource(id = R.drawable.login_image),
            contentDescription = "Login Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 입력 필드 및 버튼
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 전화번호 입력
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it.filter { char -> char.isDigit() } },
                label = { Text("전화번호") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 비밀번호 입력
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("비밀번호") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // 자동 로그인 체크박스
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = autoLoginEnabled,
                    onCheckedChange = { autoLoginEnabled = it }
                )
                Text("자동 로그인")
            }
            Spacer(modifier = Modifier.height(16.dp))

            // 로그인 버튼
            Button(
                onClick = {
                    coroutineScope.launch {
                        if (phoneNumber.isEmpty() || password.isEmpty()) {
                            showEmptyFieldsPopup = true
                        } else if (isNetworkAvailable(context)) {
                            val success = AuthRepository.login(context, phoneNumber, password)
                            if (success) {
                                AutoLoginManager.saveLoginInfo(context, phoneNumber, password, autoLoginEnabled)
                                onLoginClick(true)
                            } else {
                                showInvalidCredentialsPopup = true
                            }
                        } else {
                            showNetworkErrorPopup = true
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("로그인")
            }

            // 입력 오류 팝업
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

            // 로그인 실패 팝업
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

            // 비밀번호 찾기 버튼
            TextButton(onClick = onForgotPasswordClick) {
                Text("비밀번호 찾기")
            }
        }
    }
}