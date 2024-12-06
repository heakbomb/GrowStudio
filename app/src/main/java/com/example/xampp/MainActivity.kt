package com.example.xampp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import com.example.xampp.utils.NetworkUtils;
import android.util.Log;
import android.widget.TextView;

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button_send_request)
        val textView = findViewById<TextView>(R.id.text_response)
        button.setOnClickListener {
            // 서버 URL 설정 (업로드된 PHP 파일을 사용한 서버 URL)
            val url = "http://wonrdc.duckdns.org/ServerApi.php?action=get&user_id=1"

            // 네트워크 요청 보내기
            NetworkUtils.sendHttpRequest(
                context = this,
                url = url,
                onSuccess = { response ->
                    Log.d("Response", response)
                    textView.text = "Success: $response"
                },
                onError = { error ->
                    Log.d("Error", error)
                    textView.text = "Error: $error"
                }
            )
        }
    }
}
