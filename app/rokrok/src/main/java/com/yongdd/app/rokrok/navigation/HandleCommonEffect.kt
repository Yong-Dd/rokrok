package com.yongdd.app.rokrok.navigation

import android.app.Activity
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yongdd.core.common.log.Logger
import com.yongdd.core.ui.base.COMMON_EFFECTS_KEY
import com.yongdd.core.ui.base.CommonEffect
import com.yongdd.core.ui.base.CommonEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.collect

@Composable
fun HandleCommonEffect(
    commonEffectFlow: Flow<CommonEffect>,
    navController: NavController,
    onCommonEventSent: (event: CommonEvent) -> Unit
) {
    val context = LocalContext.current
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Logger.d("google sign launcher called ${result.data?.data}","DDD")
        GoogleSignIn.getSignedInAccountFromIntent(result.data)
            .addOnSuccessListener {
                it.id?.let { id ->
                    onCommonEventSent(CommonEvent.GoogleLoginSuccessResult(id))
                } ?: onCommonEventSent(CommonEvent.GoogleLoginFailResult("id is null"))
            }.addOnFailureListener {
                onCommonEventSent(CommonEvent.GoogleLoginFailResult(it.toString()))
                Logger.d("google sign Fail : $it","DDD")
            }
    }


    LaunchedEffect(COMMON_EFFECTS_KEY) {
        commonEffectFlow.onEach { effect ->
            when (effect) {
                is CommonEffect.GoogleLoginEffect -> {
                    Logger.d("google login effect called","DDD")
                    val signInIntent = GoogleSignIn.getClient(context, gso).signInIntent
                    googleSignInLauncher.launch(signInIntent)
                }
                is CommonEffect.Navigation.NavigateApplicationExit -> {
                    (navController.context as? Activity)?.finish()
                }
                is CommonEffect.Navigation.NavigateBack -> {
                    navController.popBackStack()
                }
                is CommonEffect.Navigation.NavigateMain -> {
                    //todo : 차후 추가 및 필요 없을 시 삭제
                }
            }
        }.collect()
    }
}