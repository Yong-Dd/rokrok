package com.yongdd.core.ui.dialog

import androidx.compose.runtime.Composable

@Composable
fun ErrorDialog(
    title : String? = null,
    message : String? = null,
    errorMessage : String? = null,
    confirmText : String = "",
    dismissText : String? = "",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {

}