package com.glowstudio.android.blindsjn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.glowstudio.android.blindsjn.ui.navigation.AppNavHost
import androidx.compose.ui.tooling.preview.Preview
import com.glowstudio.android.blindsjn.ui.theme.BlindSJNTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BlindSJNTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    AppNavHost(navController = navController)
                }
            }
        }
    }
}