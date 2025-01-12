package com.glowstudio.android.blindsjn.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun WritePostScreen(navController: NavController) {
    // 입력 필드 상태
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    // 체크박스 상태
    var isAnonymous by remember { mutableStateOf(false) }
    var isQuestion by remember { mutableStateOf(false) }

    // 포커스 요청자
    val contentFocusRequester = remember { FocusRequester() }

    Scaffold(
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                // 제목 입력 필드
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = { Text("제목을 입력해주세요.") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { contentFocusRequester.requestFocus() } // 포커스를 내용 입력 필드로 이동
                    )
                )

                // 내용 입력 필드
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    placeholder = { Text("학교 친구들과 자유롭게 얘기해보세요.\n#수강신청 #취업") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(vertical = 8.dp)
                        .focusRequester(contentFocusRequester), // 포커스 요청자 연결
                    maxLines = Int.MAX_VALUE
                )

                // 하단 버튼들
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 이미지 및 파일 첨부 버튼
                    Row {
                        IconButton(onClick = { /* TODO: 이미지 업로드 동작 */ }) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = "이미지 첨부"
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp)) // 버튼 간 간격 추가
                        IconButton(onClick = { /* TODO: 파일 업로드 동작 */ }) {
                            Icon(
                                imageVector = Icons.Default.AttachFile,
                                contentDescription = "파일 첨부"
                            )
                        }
                    }

                    // 익명 및 질문 체크박스
                    Row {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { isQuestion = !isQuestion }
                        ) {
                            Checkbox(checked = isQuestion, onCheckedChange = null)
                            Text("질문")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { isAnonymous = !isAnonymous }
                        ) {
                            Checkbox(checked = isAnonymous, onCheckedChange = null)
                            Text("익명")
                        }
                    }
                }

                // 완료 버튼
                Button(
                    onClick = {
                        if (title.isBlank() || content.isBlank()) {
                            // TODO: 제목 또는 내용이 비어 있을 경우 처리
                            println("제목과 내용을 모두 입력해야 합니다.")
                        } else {
                            // TODO: 입력 데이터 서버로 전송
                            navController.navigateUp() // 완료 후 이전 화면으로 이동
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("완료")
                }
            }
        }
    )
}
