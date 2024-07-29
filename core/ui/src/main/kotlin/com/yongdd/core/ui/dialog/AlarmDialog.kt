package com.yongdd.core.ui.dialog

import androidx.compose.runtime.Composable

@Composable
fun AlarmDialog(
    title : String? = null,
    alarmMessage : String? = null,
    confirmText : String? = "",
    dismissText : String? = "",
    onConfirm: () -> Unit = { },
    onDismiss: () -> Unit = { }
) {
}