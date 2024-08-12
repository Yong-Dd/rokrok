package com.yongdd.presentation.start.login.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            loadState = loadState,
            onEventSent = onEventSent
        )
    }

}

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    uiState: LoginContract.State,
    loadState: LoadState,
    onEventSent: (event: LoginContract.Event) -> Unit
) {
    Box(
        modifier = modifier
            .paint(
                painterResource(id = R.drawable.img_login_bg),
                contentScale = ContentScale.Crop
            )
    ) {

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
                .fillMaxHeight(0.47f)
                .padding(bottom = 155.5.dp)
        )

        // 로그인 화면
        AnimatedVisibility(
            modifier =  Modifier.align(Alignment.Center),
            visible = !uiState.isShowWriteNickNamePopUp,
            exit = fadeOut(animationSpec = tween(400))
        ) {
            LoginBox(
                onGoogleLoginClicked = {
                    onEventSent(LoginContract.Event.GoogleLoginButtonClicked)
                }
            )
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

        // 닉네임 작성
        AnimatedVisibility(
            modifier =  Modifier.align(Alignment.TopCenter),
            visible = uiState.isShowWriteNickNamePopUp,
            enter = fadeIn(animationSpec = tween(400))
        ) {
            LoginWriteNickNameBox(
                nickName =  uiState.nickName,
                nickNameMaxLength = uiState.nickNameMaxLength,
                onNickNameChanged = {
                    onEventSent(LoginContract.Event.NickNameChanged(it))
                },
                onNickNameSaveButtonClicked = {
                    onEventSent(LoginContract.Event.NickNameSaveButtonClicked)
                }
            )
        }


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

@Composable
fun LoginBox(
    modifier : Modifier = Modifier,
    onGoogleLoginClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth(0.8f)
            .fillMaxHeight(0.75f)
            .padding(top = 48.dp)
            .paint(
                painter = painterResource(id = R.drawable.img_login_box),
                contentScale = ContentScale.FillHeight
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_login_logo),
            contentDescription = "logo : RokRok",
            modifier = Modifier.fillMaxWidth(0.74f),
            contentScale = ContentScale.FillWidth
        )
        Text(
            text = stringResource(id = R.string.login_app_message),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 19.2.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 23.sp
            ),
            color = Color.White,
            modifier = Modifier.padding(top = 24.dp, bottom = 59.5.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.img_google_btn),
            contentDescription = "google sign in button",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth(0.63f)
                .clickable {
                    onGoogleLoginClicked()
                }
        )
    }
}

@Composable
fun LoginWriteNickNameBox(
     modifier : Modifier = Modifier,
     nickName: String,
     nickNameMaxLength: Int,
     onNickNameChanged: (String) -> Unit,
     onNickNameSaveButtonClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth(0.8f)
            .fillMaxHeight(0.66f)
            .padding(top = 145.dp)
            .paint(
                painter = painterResource(id = R.drawable.img_login_box_small),
                contentScale = ContentScale.FillHeight
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_login_logo),
            contentDescription = "logo : RokRok",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth(0.74f)
                .padding(top = 50.dp)
        )
        Text(
            text = stringResource(id = R.string.write_your_nickname),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 19.2.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 23.sp
            ),
            color = Color.White,
            modifier = Modifier.padding(top = 75.8.dp, bottom = 22.dp)
        )

        Box(
            modifier = Modifier
                .padding(bottom = 24.dp)
                .fillMaxWidth(0.74f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_login_textfield),
                contentDescription = "text field",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
            BasicTextField(
                value = nickName,
                onValueChange = {
                    if(it.length <= nickNameMaxLength) onNickNameChanged(it)
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            )
        }

        Button(
            shape = RoundedCornerShape(40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            border = BorderStroke(2.dp, Color.White),
            onClick = {
                onNickNameSaveButtonClicked()
            }
        ) {
            Text(
                text= stringResource(id = R.string.confirm),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 19.2.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 23.sp
                ),
            )
        }
    }
}


@Preview
@Composable
fun LoginScreenPreview() {
    RokRokTheme {
        LoginScreenContent(
            modifier = Modifier.background(Color.White),
            uiState = LoginContract.State(isShowWriteNickNamePopUp = false, nickName = "김디디"),
            loadState = LoadState.Idle,
            onEventSent = {},
        )
    }
}

