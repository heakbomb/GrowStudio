package com.glowstudio.android.blindsjn.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.glowstudio.android.blindsjn.ui.MainScreen
import com.glowstudio.android.blindsjn.ui.screens.*

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "login"

    ) {
        // 로그인 화면
        composable("login") {
            LoginScreen(
                onLoginClick = {
                    navController.navigate("main") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onSignupClick = { navController.navigate("signup") },
                onForgotPasswordClick = { /* TODO: 비밀번호 찾기 화면 이동 */ }
            )
        }

        // 회원가입 화면
        composable("signup") {
            SignupScreen(
                onSignupClick = { phoneNumber, password ->
                    // TODO: 회원가입 로직 추가
                    navController.navigate("main") {
                        popUpTo("signup") { inclusive = true }
                    }
                },
                onBackToLoginClick = { navController.navigateUp() }
            )
        }

        // 메인 화면
        composable("main") {
            MainScreen(navController)
        }

        // 홈 화면
        composable("home") {
            HomeScreen()
        }

        // 게시판 목록 화면
        composable("board") {
            BoardScreen(navController)
        }

        // 게시판 상세 화면
        composable("boardDetail/{title}") { backStackEntry ->
            val postTitle = backStackEntry.arguments?.getString("title") ?: "게시글"
            BoardDetailScreen(navController, title = postTitle)
        }

        // 게시글 작성 화면
        composable("writePost") {
            WritePostScreen(navController)
        }

        // 인기글 화면
        composable("popular") {
            PopularScreen()
        }

        // 메시지 화면
        composable("message") {
            MessageScreen()
        }

        // 프로필 화면 (로그아웃 클릭 이벤트 추가)
        composable("profile") {
            ProfileScreen(
                onLogoutClick = {
                    navController.navigate("login") {
                        popUpTo("main") { inclusive = true }
                    }
                }
            )
        }
    }
}