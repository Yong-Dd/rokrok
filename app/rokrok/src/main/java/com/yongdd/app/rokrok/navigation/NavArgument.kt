package com.yongdd.app.rokrok.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.yongdd.core.common.consts.ArgName

object NavArgument {
    val argUserId = navArgument(ArgName.nameUserId) {
        type = NavType.StringType
        defaultValue = ""
    }
}