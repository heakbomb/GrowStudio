package net.flow9.kotlin.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.flow9.kotlin.myapplication.di.RetrofitInstance
import net.flow9.kotlin.myapplication.model.BusinessStatusResponse
import net.flow9.kotlin.myapplication.model.BusinessRequest
import net.flow9.kotlin.myapplication.ui.theme.MyApplicationTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URLEncoder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BusinessStatusScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun BusinessStatusScreen(modifier: Modifier = Modifier) {
    var businessNumber by remember { mutableStateOf("") }
    var statusMessage by remember { mutableStateOf("결과가 여기에 표시됩니다.") }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicTextField(
            value = businessNumber,
            onValueChange = { businessNumber = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
                    singleLine = true
        )

        Button(
            onClick = {
                if (businessNumber.isNotEmpty() && businessNumber.length == 10) {
                    coroutineScope.launch(Dispatchers.IO) {
                        checkBusinessStatus(businessNumber) { status ->
                            statusMessage = status
                        }
                    }
                } else {
                    statusMessage = "올바른 사업자등록번호를 입력하세요. (10자리 숫자)"
                }
            }
        ) {
            Text(text = "조회하기")
        }

        Text(text = statusMessage)
    }
}


fun checkBusinessStatus(businessNumber: String, onResult: (String) -> Unit) {
    val serviceKey = "data-portal-test-key" // 테스트용 API 키
    val requestBody = BusinessRequest(
        b_no = listOf(businessNumber) // 요청 Body: 사업자등록번호 리스트
    )

    RetrofitInstance.api.getBusinessStatus(serviceKey, requestBody)
        .enqueue(object : retrofit2.Callback<BusinessStatusResponse> {
            override fun onResponse(
                call: Call<BusinessStatusResponse>,
                response: Response<BusinessStatusResponse>
            ) {
                if (response.isSuccessful) {
                    val businessData = response.body()?.data?.firstOrNull()
                    if (businessData != null) {
                        onResult("상태: ${businessData.businessStatus}\n업종: ${businessData.taxType}")
                    } else {
                        onResult("사업자 정보를 찾을 수 없습니다.")
                    }
                } else {
                    onResult("응답 실패: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<BusinessStatusResponse>, t: Throwable) {
                onResult("API 호출 실패: ${t.localizedMessage}")
            }
        })
}


@Preview(showBackground = true)
@Composable
fun BusinessStatusScreenPreview() {
    MyApplicationTheme {
        BusinessStatusScreen()
    }
}
