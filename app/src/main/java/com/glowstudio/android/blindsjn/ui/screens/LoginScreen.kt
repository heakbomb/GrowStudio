package com.glowstudio.android.blindsjn.ui.screens

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
import com.glowstudio.android.blindsjn.R

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit,
    onSignupClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    // 상태 변수
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

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
            Button(
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("로그인")
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
