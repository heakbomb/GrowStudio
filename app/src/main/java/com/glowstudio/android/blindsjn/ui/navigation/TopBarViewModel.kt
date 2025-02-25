/**

package com.glowstudio.android.blindsjn.ui.navigation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 상단바 상태 데이터 클래스
 */
data class TopBarState(
    val title: String,
    val showBackButton: Boolean,
    val showSearchButton: Boolean
)

/**
 * 상단바 상태를 관리하는 ViewModel
 */
class TopBarViewModel : ViewModel() {
    private val _topBarState = MutableStateFlow(TopBarState("홈 화면", false, false))
    val topBarState = _topBarState.asStateFlow()

    fun updateState(newState: TopBarState) {
        _topBarState.value = newState
    }
}
**/