package com.xp.lib_common_ui.utils

import android.app.ProgressDialog
import android.content.Context

object DialogUtil {
    fun getWaitingDialog(context: Context, message: String): ProgressDialog {
        val dialog = ProgressDialog(context)
        dialog.setMessage(message)
        return dialog
    }
}