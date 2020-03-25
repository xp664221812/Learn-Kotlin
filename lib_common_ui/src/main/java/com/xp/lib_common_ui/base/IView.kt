package com.xp.lib_common_ui.base

import com.blankj.utilcode.util.ToastUtils

interface IView {
    /**
     *显示加载
     */
    fun showLoading()

    /**
     *隐藏加载
     */
    fun hideLoading()


    /**
     *显示错误信息
     */
    fun showError(err: String)


    /**
     *显示信息
     */
    fun showMsg(msg: String)

    fun showToast(msg: String) {
        ToastUtils.showShort(msg)
    }

}