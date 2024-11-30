package com.glowstudio.android.blindsjn.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SignupScreen(
    onSignupClick: (String, String, String) -> Unit, // 전화번호, 업종, 경력을 전달
    onBackToLoginClick: () -> Unit
) {
    var phoneNumber by remember { mutableStateOf("") }
    var industry by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // "회원가입" 텍스트
        Text(
            text = "회원가입",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 전화번호 입력
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("전화번호") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 업종 입력
        OutlinedTextField(
            value = industry,
            onValueChange = { industry = it },
            label = { Text("업종 (예: 카페, 음식점 등)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 경력 입력
        OutlinedTextField(
            value = experience,
            onValueChange = { experience = it },
            label = { Text("경력 (년)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 회원가입 버튼
        Button(
            onClick = { onSignupClick(phoneNumber, industry, experience) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("회원가입")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 로그인 화면으로 돌아가기
        TextButton(onClick = onBackToLoginClick) {
            Text("이미 계정이 있으신가요? 로그인")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignupScreenPreview() {
    SignupScreen(
        onSignupClick = { _, _, _ -> },
        onBackToLoginClick = {}
    )
}
