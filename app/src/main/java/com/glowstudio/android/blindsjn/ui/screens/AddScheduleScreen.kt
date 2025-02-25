package com.glowstudio.android.blindsjn.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.glowstudio.android.blindsjn.tempData.ScheduleInput

@Composable
fun AddScheduleScreen(
    onCancel: () -> Unit,
    onSave: (ScheduleInput) -> Unit
) {
    // 스케줄 입력 상태 (DB 없이, 화면 내에서만 유지)
    var title by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var memo by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text("스케줄 추가") },
                actions = {
                    IconButton(onClick = {
                        // 저장 로직
                        val schedule = ScheduleInput(
                            title = title,
                            startDate = startDate,
                            endDate = endDate,
                            startTime = startTime,
                            endTime = endTime,
                            memo = memo
                        )
                        onSave(schedule)  // 스케줄 정보 콜백으로 전달
                    }) {
                        Icon(Icons.Filled.Save, contentDescription = "저장")
                    }
                    IconButton(onClick = onCancel) {
                        Icon(Icons.Filled.Close, contentDescription = "취소")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 제목
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("제목") },
                modifier = Modifier.fillMaxWidth()
            )

            // 시작일
            OutlinedTextField(
                value = startDate,
                onValueChange = { startDate = it },
                label = { Text("시작일") },
                modifier = Modifier.fillMaxWidth()
            )

            // 종료일
            OutlinedTextField(
                value = endDate,
                onValueChange = { endDate = it },
                label = { Text("종료일") },
                modifier = Modifier.fillMaxWidth()
            )

            // 시작 시간
            OutlinedTextField(
                value = startTime,
                onValueChange = { startTime = it },
                label = { Text("시작 시간") },
                modifier = Modifier.fillMaxWidth()
            )

            // 종료 시간
            OutlinedTextField(
                value = endTime,
                onValueChange = { endTime = it },
                label = { Text("종료 시간") },
                modifier = Modifier.fillMaxWidth()
            )

            // 메모
            OutlinedTextField(
                value = memo,
                onValueChange = { memo = it },
                label = { Text("메모") },
                modifier = Modifier.fillMaxWidth()
            )

            // 하단 버튼 (취소 / 저장)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = onCancel) {
                    Text("취소")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    val schedule = ScheduleInput(
                        title = title,
                        startDate = startDate,
                        endDate = endDate,
                        startTime = startTime,
                        endTime = endTime,
                        memo = memo
                    )
                    onSave(schedule)
                }) {
                    Text("저장")
                }
            }
        }
    }
}
