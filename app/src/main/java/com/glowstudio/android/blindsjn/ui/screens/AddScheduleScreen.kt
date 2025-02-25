package com.glowstudio.android.blindsjn.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.glowstudio.android.blindsjn.tempData.ScheduleInput
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import com.glowstudio.android.blindsjn.ui.dialog.InlineTimePicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScheduleScreen(
    onCancel: () -> Unit,
    onSave: (ScheduleInput) -> Unit
) {
    // 스케줄 입력 상태
    var title by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var memo by remember { mutableStateOf("") }

    // 시작일/종료일 선택 다이얼로그 상태 (날짜 선택은 그대로 DatePickerDialog 사용)
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }

    // DatePickerState 생성
    val startDatePickerState = rememberDatePickerState(initialSelectedDateMillis = null)
    val endDatePickerState = rememberDatePickerState(initialSelectedDateMillis = null)

    // 타임피커 다이얼로그 대신, 인라인 타임피커 UI를 사용할지 여부 (토글로 제어)
    var showStartTimePickerInline by remember { mutableStateOf(false) }
    var showEndTimePickerInline by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("스케줄 추가") },
                actions = {
                    IconButton(onClick = {
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
            // 제목 입력
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("제목") },
                modifier = Modifier.fillMaxWidth()
            )

            // 시작일 / 종료일 (같은 줄)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = startDate,
                    onValueChange = { },
                    label = { Text("시작일") },
                    modifier = Modifier
                        .weight(1f)
                        .clickable { showStartDatePicker = true },
                    readOnly = true
                )
                OutlinedTextField(
                    value = endDate,
                    onValueChange = { },
                    label = { Text("종료일") },
                    modifier = Modifier
                        .weight(1f)
                        .clickable { showEndDatePicker = true },
                    readOnly = true
                )
            }

            // 시작시간 / 종료시간 (같은 줄) - 인라인 타임피커 토글
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = startTime,
                    onValueChange = { },
                    label = { Text("시작 시간") },
                    modifier = Modifier
                        .weight(1f)
                        .clickable { showStartTimePickerInline = !showStartTimePickerInline },
                    readOnly = true
                )
                OutlinedTextField(
                    value = endTime,
                    onValueChange = { },
                    label = { Text("종료 시간") },
                    modifier = Modifier
                        .weight(1f)
                        .clickable { showEndTimePickerInline = !showEndTimePickerInline },
                    readOnly = true
                )
            }

            // 인라인 시작 시간 타임피커 (토글에 따라 표시)
            if (showStartTimePickerInline) {
                InlineTimePicker(
                    initialHour = if (startTime.isNotEmpty()) startTime.substringBefore(":").toInt() else 13,
                    initialMinute = if (startTime.isNotEmpty()) startTime.substringAfter(":").toInt() else 0
                ) { hour, minute ->
                    startTime = String.format("%02d:%02d", hour, minute)
                    showStartTimePickerInline = false
                }
            }

            // 인라인 종료 시간 타임피커 (토글에 따라 표시)
            if (showEndTimePickerInline) {
                InlineTimePicker(
                    initialHour = if (endTime.isNotEmpty()) endTime.substringBefore(":").toInt() else 13,
                    initialMinute = if (endTime.isNotEmpty()) endTime.substringAfter(":").toInt() else 0
                ) { hour, minute ->
                    endTime = String.format("%02d:%02d", hour, minute)
                    showEndTimePickerInline = false
                }
            }

            // 메모 입력
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

    // 시작일 DatePickerDialog (날짜 선택 모달)
    if (showStartDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showStartDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val selectedMillis = startDatePickerState.selectedDateMillis
                    if (selectedMillis != null) {
                        val selectedDateLocal: LocalDate = Instant.ofEpochMilli(selectedMillis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        startDate = selectedDateLocal.toString()
                    }
                    showStartDatePicker = false
                }) {
                    Text("확인")
                }
            },
            dismissButton = {
                TextButton(onClick = { showStartDatePicker = false }) {
                    Text("취소")
                }
            }
        ) {
            DatePicker(state = startDatePickerState)
        }
    }

    // 종료일 DatePickerDialog (날짜 선택 모달)
    if (showEndDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showEndDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val selectedMillis = endDatePickerState.selectedDateMillis
                    if (selectedMillis != null) {
                        val selectedDateLocal: LocalDate = Instant.ofEpochMilli(selectedMillis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        endDate = selectedDateLocal.toString()
                    }
                    showEndDatePicker = false
                }) {
                    Text("확인")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEndDatePicker = false }) {
                    Text("취소")
                }
            }
        ) {
            DatePicker(state = endDatePickerState)
        }
    }
}
