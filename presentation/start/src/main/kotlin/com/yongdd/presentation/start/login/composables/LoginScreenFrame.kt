package com.yongdd.presentation.start.login.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yongdd.core.ui.base.BaseScreen
import com.yongdd.core.ui.base.CommonEvent
import com.yongdd.core.ui.base.LoadState
import com.yongdd.core.ui.base.SIDE_EFFECTS_KEY
import com.yongdd.core.ui.theme.RokRokTheme
import com.yongdd.presentation.start.R
import com.yongdd.presentation.start.login.LoginContract
import com.yongdd.presentation.start.login.Navigation.Routes.LOGIN
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun LoginScreenFrame(
    uiState: LoginContract.State,
    loadState: LoadState,
    effectFlow: SharedFlow<LoginContract.Effect>?,
    onCommonEventSent: (event: CommonEvent) -> Unit,
    onEventSent: (event: LoginContract.Event) -> Unit,
    onNavigationRequested: (LoginContract.Effect.Navigation) -> Unit
) {
    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect: LoginContract.Effect ->
            when (effect) {
                is LoginContract.Effect.Navigation -> {
                    onNavigationRequested(effect)
                }
            }
        }?.collect()
    }

    BaseScreen(
        loadState = loadState,
        description = LOGIN,
        isOverlayTopBar = false
    ) {
        LoginScreenContent(
            modifier = Modifier.fillMaxSize(),
            uiState = uiState,
            loadState = loadState
        )
    }

}

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    uiState: LoginContract.State,
    loadState: LoadState
) {
    Box(modifier = modifier
        .paint(
            painterResource(id = R.drawable.img_login_bg),
            contentScale = ContentScale.Crop
        )) {

        // 왼쪽 가운데 k
        Image(
            painter = painterResource(id = R.drawable.img_k_top),
            contentDescription = "",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .fillMaxHeight(0.32f)
                .padding(top = 17.3.dp)
        )

        // 왼쪽 아래 o
        Image(
            painter = painterResource(id = R.drawable.img_o_bottom),
            contentDescription = "",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxHeight(0.36f)
        )

        // 오른쪽 위 o
        Image(
            painter = painterResource(id = R.drawable.img_o_top),
            contentDescription = "",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .fillMaxHeight(0.52f)
                .padding(top = 147.8.dp)
        )

        // 오른쪽 가운데 R
        Image(
            painter = painterResource(id = R.drawable.img_r_bottom),
            contentDescription = "",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .fillMaxHeight(0.46f)
                .padding(bottom = 155.5.dp)
        )


        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.74f)
                .padding(top = 48.dp)
                .paint(
                    painter = painterResource(id = R.drawable.img_login_box),
                    contentScale = ContentScale.FillHeight
                )
        ) {

        }



        // 왼쪽 위 R
        Image(
            painter = painterResource(id = R.drawable.img_r_top),
            contentDescription = "",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxHeight(0.34f)
                .padding(top = 6.7.dp)
        )

        // 오른쪽 아래 k
        Image(
            painter = painterResource(id = R.drawable.img_k_bottom),
            contentDescription = "",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .fillMaxHeight(0.19f)
                .padding(bottom = 33.3.dp)
        )
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    RokRokTheme {
        LoginScreenContent(
            modifier = Modifier.background(Color.White),
            uiState = LoginContract.State(),
            loadState = LoadState.Idle
        )
    }
}

