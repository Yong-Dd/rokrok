package com.yongdd.core.ui.base

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yongdd.core.ui.dialog.AlarmDialog
import com.yongdd.core.ui.dialog.ErrorDialog
import com.yongdd.core.ui.dialog.Loading

@Composable
fun BaseScreen(
    modifier : Modifier = Modifier,
    description : String = "BaseScreen",
    containerColor : Color? = null,
    statusBarColor : Color? = null,
    isStatusBarTextDark : Boolean = true,

    // top bar
    isOverlayTopBar : Boolean = false,
    topBar: @Composable (() -> Unit)? = null,

    // drawer
    drawerState : DrawerState,
    drawerContent : @Composable () -> Unit,

    // ui state
    loadState : LoadState,

    content : @Composable (paddingValues : PaddingValues) -> Unit
)
{
    statusBarColor?.let { color ->
        LocalView.current.changeStatusBarColor(color = color, isStatusBarTextDark = isStatusBarTextDark)
    }

    ModalNavigationDrawer(
        // drawer
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerShape = RectangleShape,
                content = {
                    drawerContent()
                }
            )
        },
        gesturesEnabled = drawerState.isOpen,
        content = {
            BaseContent(
                modifier = modifier.semantics {
                    contentDescription = description
                    testTag = loadState.javaClass.simpleName
                },
                containerColor = containerColor,

                isOverlayTopBar = isOverlayTopBar,
                topBar = topBar,

                content = content
            )
        })

    CommonStateProcess(loadState)
}

@Composable
fun BaseScreen(
    modifier : Modifier = Modifier,
    description : String = "BaseScreen",
    containerColor : Color? = null,
    statusBarColor : Color? = null,
    isStatusBarTextDark : Boolean = true,

    isOverlayTopBar : Boolean = false,
    topBar: @Composable (() -> Unit)? = null,

    // ui state
    loadState : LoadState,

    content : @Composable (paddingValues : PaddingValues) -> Unit
) {

    statusBarColor?.let { color ->
        LocalView.current.changeStatusBarColor(color = color, isStatusBarTextDark = isStatusBarTextDark)
    }



    BaseContent(
        modifier = modifier.semantics {
            contentDescription = description
            testTag = loadState.javaClass.simpleName
        },
        containerColor = containerColor,
        isOverlayTopBar = isOverlayTopBar,
        topBar = topBar,
        content = content
    )

    CommonStateProcess(loadState)
}

@Composable
fun CommonStateProcess(
    loadState : LoadState
) {
    val context = LocalContext.current
    when(loadState){
        is LoadState.Loading -> {
            Loading(
                loadingMessage = loadState.message
            )
        }
        is LoadState.ErrorDialog -> {
            ErrorDialog(
                title = loadState.title?.asString(context),
                message = loadState.message?.asString(context),
                errorMessage = loadState.errorMessage?.asString(context),
                confirmText = loadState.confirmText.asString(context),
                dismissText = loadState.dismissText?.asString(context),
                onConfirm = loadState.onConfirm,
                onDismiss = loadState.onDismiss
            )
        }
        is LoadState.AlarmDialog -> {
            AlarmDialog(
                title = loadState.title?.asString(context),
                alarmMessage = loadState.message?.asString(context),
                confirmText = loadState.confirmText?.asString(context),
                dismissText = loadState.dismissText?.asString(context),
                onConfirm = loadState.onConfirm,
                onDismiss = loadState.onDismiss
            )
        }

        else -> {}
    }
}

@Composable
fun BaseContent(
    modifier : Modifier = Modifier,
    containerColor : Color? = null,

    isOverlayTopBar : Boolean = false,
    topBar: @Composable (() -> Unit)? = null,
    topBarHeight: Dp = 0.dp,

    content : @Composable (paddingValues : PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        containerColor = containerColor ?: MaterialTheme.colorScheme.background,

        // top bar
        topBar = {
            if(topBar != null){
                topBar()
            }
        },
        content = {
            Column(modifier = Modifier
                .padding(top = if (isOverlayTopBar || topBar == null) 0.dp else topBarHeight)
                .fillMaxSize()) {
                content(it)
            }
        }
    )
}

