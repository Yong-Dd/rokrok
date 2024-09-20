package com.yongdd.presentation.main.main.routine.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yongdd.presentation.main.R
import com.yongdd.presentation.main.main.main.MainContract

@Composable
fun MainRoutineScreenFrame(
    mainRoutine : MainContract.MainScreen.MainRoutine,
    onRoutineClick: () -> Unit
) {

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