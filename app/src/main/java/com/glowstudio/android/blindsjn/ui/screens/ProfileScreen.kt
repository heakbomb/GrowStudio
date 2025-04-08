package com.glowstudio.android.blindsjn.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.glowstudio.android.blindsjn.network.AutoLoginManager
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onLogoutClick: () -> Unit,
    onBusinessCertificationClick: () -> Unit,
    onProfileEditClick: () -> Unit,
    onContactEditClick: () -> Unit,
    onFoodCostCalculatorClick: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var showLogoutPopup by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 프로필 섹션
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF5F5F5)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 프로필 이미지 플레이스홀더
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE0E0E0))
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "사용자",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "@username",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
        }

        // 메뉴 아이템들
        LazyColumn {
            items(menuItems) { item ->
                MenuListItem(
                    title = item,
                    onClick = {
                        when (item) {
                            "프로필 변경" -> onProfileEditClick()
                            "연락처 변경" -> onContactEditClick()
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // 푸드코스트 계산 버튼 추가
        Button(
            onClick = { onFoodCostCalculatorClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "푸드코스트 계산")
        }

        // 기존 버튼들
        Button(
            onClick = { onBusinessCertificationClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "사업자 인증")
        }

        Button(
            onClick = { showLogoutPopup = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "로그아웃")
        }
    }

    // 로그아웃 팝업
    if (showLogoutPopup) {
        AlertDialog(
            onDismissRequest = { showLogoutPopup = false },
            title = { Text("로그아웃") },
            text = { Text("로그아웃하시겠습니까?") },
            confirmButton = {
                TextButton(onClick = {
                    coroutineScope.launch {
                        try {
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
            dismissButton = {
                TextButton(onClick = { showLogoutPopup = false }) {
                    Text("취소")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MenuListItem(
    title: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title)
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}

// 필요한 내용 여기에 넣으면 됨.
private val menuItems = listOf(
    "프로필 변경",
    "연락처 변경"
)

@Preview(showBackground = true)
@Composable
fun ScreenPreview_() {
    ProfileScreen(
        onLogoutClick = { },
        onBusinessCertificationClick = { },
        onProfileEditClick = { },
        onContactEditClick = { },
        onFoodCostCalculatorClick = { }
    )
}