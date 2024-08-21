package com.yongdd.app.rokrok.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import com.yongdd.app.rokrok.navigation.AppNavigation
import com.yongdd.core.ui.theme.RokRokTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.light(Color.White.copy(alpha = 0.1f).hashCode(), Color.White.copy(alpha = 0.1f).hashCode())
        )
//        WindowCompat.setDecorFitsSystemWindows(window, false)
//        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightNavigationBars = true // 탐색 버튼 아이콘을 밝은 색으로 설정

        setContent {
            RokRokTheme {
                AppNavigation()
            }
        }
        // todo 1 : id 값을 통해 가져와야함(있는지 확인해야함)
        // todo 2 : 값을 업뎃 및 추가해야함 (여기서도 id 값이 필요함)



    }
}