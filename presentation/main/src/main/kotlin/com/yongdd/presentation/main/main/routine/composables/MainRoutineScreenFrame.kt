package com.yongdd.presentation.main.main.routine.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yongdd.presentation.main.R
import com.yongdd.presentation.main.main.main.MainContract

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainRoutineScreenFrame(
    modifier: Modifier = Modifier,
    mainRoutine : MainContract.MainScreen.MainRoutine,
    onRoutineClick: () -> Unit
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { mainRoutine.routineList.size })

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // 상단 날짜 부분
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            /**
             * 양쪽 버튼 및 해당 월
             * -> 해당 월 기준 체크하기 (선택한 일자의 월에 따라감)
             * */
            Text(
                text = mainRoutine.currentDate?:"", // 년과 월이 나오도록 수정
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier =  Modifier.fillMaxWidth()
            )
            /**
             * 날짜 리스트
             * - 일부터 시작인지 월부터 시작인지 여부 체크
             * - 스크롤에 따라 변경
             * */

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth(),
            ) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 19.dp),
                    horizontalArrangement = Arrangement.spacedBy(38.dp)
                ) {

                }
            }

        }

        // 하단 리스트 및 버튼 부분
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            val maxWidth : Dp = this.maxWidth
            val buttonSize : Dp = maxWidth * 0.144f
            LazyColumn(
                contentPadding = PaddingValues(vertical = 21.dp)
            ) {
                // todo : holder
            }
            Image(
                painter = painterResource(id = R.drawable.img_floating_btn),
                contentDescription = "Add routine button",
                modifier = Modifier
                    .padding(bottom = 60.dp)
                    .size(buttonSize)
                    .align(Alignment.BottomCenter)
            )
        }
    }

}