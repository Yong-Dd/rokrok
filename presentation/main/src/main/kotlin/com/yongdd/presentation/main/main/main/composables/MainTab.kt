package com.yongdd.presentation.main.main.main.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yongdd.presentation.main.main.main.MainContract

@Composable
fun MainTab(
    modifier: Modifier = Modifier,
    uiState: MainContract.State,
    tabTextList : List<String>,
    tabStartPointPercentList : List<Float> = listOf(0.003f, 0.313f, 0.631f),
    currentTab : Int,
    selectedImage : Int,
    backgroundImage : Int,
    selectedTextColor : Color,
    unSelectedTextColor : Color,
    height : Int = 50,
    onTabClick:(Int) -> Unit,
    animationDuration : Int = 300
) {

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth(0.89f),
    ) {
        val maxWidth = this.maxWidth
        val imageHeight = (maxWidth * 156) / 1005

        val indicatorOffset: Dp by animateDpAsState(
            targetValue = maxWidth * tabStartPointPercentList[currentTab],
            animationSpec = tween(easing = LinearEasing, durationMillis = animationDuration),
            label = "",
        )
        Image(
            painter = painterResource(id = backgroundImage),
            contentDescription = "",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.height(imageHeight)
        )

        Image(
            painter = painterResource(id = selectedImage),
            contentDescription = "",
            modifier = Modifier
                .height(imageHeight + 3.dp)
                .offset(x = indicatorOffset),
            contentScale = ContentScale.FillHeight
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for(index in tabTextList.indices) {
                TabItem(
                    modifier = Modifier
                        .weight(1f)
                        .height(height = height.dp),
                    isSelected = currentTab == index,
                    tabText = tabTextList[index],
                    selectedTextColor = selectedTextColor,
                    unSelectedTextColor = unSelectedTextColor,
                    onTabClick = {
                        onTabClick(index)
                    }
                )
            }
        }
    }
}

@Composable
fun TabItem(
    modifier: Modifier = Modifier,
    isSelected : Boolean,
    tabText : String,
    selectedTextColor : Color,
    unSelectedTextColor : Color,
    onTabClick: () -> Unit
) {
    val tabTextColor: Color by animateColorAsState(
        targetValue = if (isSelected) {
            selectedTextColor
        } else {
            unSelectedTextColor
        },
        animationSpec = tween(easing = LinearEasing), label = "",
    )
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = tabText,
            modifier = Modifier
                .padding(bottom = 2.dp)
                .align(Alignment.Center)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onTabClick()
                },
            color = tabTextColor,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
        )
    }
}