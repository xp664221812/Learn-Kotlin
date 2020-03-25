package com.xp.learn.ext

import android.content.Context
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils

fun Any.loge(content: String?) {
    loge(this.javaClass.simpleName ?: "Learn", content ?: "")
}

fun loge(tag: String, content: String?) {
    LogUtils.e(tag, content ?: "")
}

fun Fragment.showToast(content: String) {
    ToastUtils.showShort(content)
}

fun Context.showToast(content: String) {
    ToastUtils.showShort(content)
}