package com.yongdd.presentation.main.main.main.composables

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yongdd.core.ui.base.BaseScreen
import com.yongdd.core.ui.base.CommonEvent
import com.yongdd.core.ui.base.LoadState
import com.yongdd.core.ui.base.SIDE_EFFECTS_KEY
import com.yongdd.core.ui.theme.ColorSet
import com.yongdd.core.ui.theme.RokRokTheme
import com.yongdd.domain.model.user.UserModel
import com.yongdd.presentation.main.R
import com.yongdd.presentation.main.main.diary.composables.MainDiaryScreenFrame
import com.yongdd.presentation.main.main.main.MainContract
import com.yongdd.presentation.main.main.main.Navigation.Routes.MAIN
import com.yongdd.presentation.main.main.routine.composables.MainRoutineScreenFrame
import com.yongdd.presentation.main.main.statistics.composables.MainStatisticsScreenFrame
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun MainScreenFrame(
    uiState: MainContract.State,
    loadState: LoadState,
    effectFlow: SharedFlow<MainContract.Effect>?,
    onCommonEventSent: (event: CommonEvent) -> Unit,
    onEventSent: (event: MainContract.Event) -> Unit,
    onNavigationRequested: (MainContract.Effect.Navigation) -> Unit 
) {
    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect : MainContract.Effect ->
            when(effect) {
                is MainContract.Effect.Navigation -> {
                    onNavigationRequested(effect)
                }
            }
        }?.collect()
    }

    BaseScreen(
        loadState = loadState,
        description = MAIN,
        isOverlayTopBar = true,
    ) {
        MainScreenContent(
            uiState = uiState,
            loadState = loadState,
            onEventSent = onEventSent
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    uiState: MainContract.State,
    loadState: LoadState,
    onEventSent: (event: MainContract.Event) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.img_back_main_theme1)
            )
    ) {
        val pagerState = rememberPagerState(pageCount = {3}, initialPage = 1)
        LaunchedEffect(pagerState.currentPage) {
            Log.d("DDD","page ${pagerState.currentPage}")
            onEventSent(MainContract.Event.PageChanged(pagerState.currentPage))
        }

        LaunchedEffect(uiState.currentPage) {
            if(pagerState.currentPage != uiState.currentPage) {
                pagerState.animateScrollToPage(uiState.currentPage)
            }
        }

        var isFirst by remember { mutableStateOf(true) }

        Spacer(modifier = Modifier.height(20.dp))

        // todo : 애니메이션 처음엔 안나오도록 확인
        AnimatedVisibility(
            modifier = Modifier,
            visible = pagerState.currentPage == 1,
            enter = if(isFirst) EnterTransition.None else expandVertically(animationSpec = tween(200)),
            exit = shrinkVertically(animationSpec = tween(200))
        ) {
            if(isFirst) isFirst = false
            MyInfoContent(
                modifier = Modifier.padding(top = 42.dp),
                uiState = uiState,
                onEventSent = onEventSent
            )
        }
            MainTab(
                modifier = Modifier
                    .padding(top = 22.dp)
                    .align(Alignment.CenterHorizontally),
                uiState = uiState,
                tabTextList = uiState.tabList,
                currentTab = uiState.currentPage,
                selectedImage = R.drawable.img_back_main_tab_selected,
                backgroundImage = R.drawable.img_back_main_tab,
                selectedTextColor = ColorSet.green_083D0A6,
                unSelectedTextColor = Color.White,
                onTabClick = {
                   onEventSent(MainContract.Event.PageChanged(it))
                }
            )

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> {
                        MainDiaryScreenFrame(
                            mainDiary = uiState.mainDiary,
                            onDiaryClick = {}
                        )
                    }

                    1 -> {
                        MainRoutineScreenFrame(
                            mainRoutine = uiState.mainRoutine,
                            onRoutineClick = {}
                        )
                    }

                    2 -> {
                        MainStatisticsScreenFrame(
                            mainStatistics = uiState.mainStatistics
                        )
                    }
                }
            }

        }
}

@Composable
fun MyInfoContent(
    modifier: Modifier,
    uiState: MainContract.State,
    onEventSent: (event: MainContract.Event) -> Unit
){
    Row(
        modifier = modifier.padding(start = 33.6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(58.dp)
                .clip(RoundedCornerShape(29.dp))
                .border(1.dp, Color.White, RoundedCornerShape(29.dp))
                .background(Color.White.copy(alpha = 0.2f))
        ) {
            Text(
                text = uiState.userInfo.settingEmoji ?: "",
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 2.dp),
                textAlign = TextAlign.Center,
                fontSize = 30.sp
            )
        }

        Column(
            modifier = Modifier
                .padding(start = 14.4.dp)
        ) {
            Text(
                text = uiState.userInfo.nickName ?: "",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 19.2.sp,
                    fontWeight = FontWeight.SemiBold,
                ),
                color = Color.White
            )
            Text(
                text = uiState.userInfo.message ?: "",
                modifier = Modifier.padding(top = 4.5.dp),
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    RokRokTheme {
        MainScreenContent(
            modifier = Modifier.background(Color.White),
            uiState = MainContract.State(
                userInfo = UserModel(
                    nickName = "SUPULBERRY",
                    message = "RokRok! 함께할 준비 되셨나요?🚀",
                    settingEmoji = "⚙\uFE0F"
                )
            ),
            loadState = LoadState.Idle,
            onEventSent = {},
        )
    }
}