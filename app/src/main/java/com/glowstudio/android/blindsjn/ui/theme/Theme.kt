package com.glowstudio.android.blindsjn.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.compose.ui.graphics.toArgb


// 다크 모드 색상 팔레트
private val DarkColorScheme = darkColorScheme(
    primary = CoralOrange,        // 메인 색상 (코랄 오렌지)
    secondary = SoftBeige,        // 보조 색상 (부드러운 베이지)
    tertiary = White,             // 보조 색상 (흰색)
    background = DarkBackground,  // 배경색 (다크 모드)
    surface = DarkSurface,        // 카드 배경 (다크 모드)
    onPrimary = Color.Black,      // 버튼 텍스트 (다크 모드)
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

// 라이트 모드 색상 팔레트
private val LightColorScheme = lightColorScheme(
    primary = CoralOrange,        // 메인 색상 (코랄 오렌지)
    secondary = SoftBeige,        // 보조 색상 (부드러운 베이지)
    tertiary = White,             // 보조 색상 (흰색)
    background = LightBackground, // 배경색 (라이트 모드)
    surface = LightSurface,       // 카드 배경 (라이트 모드)
    onPrimary = Color.White,      // 버튼 텍스트 (라이트 모드)
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun BlindSJNTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        val window = (view.context as Activity).window
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = colorScheme.primary.toArgb()
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
