package com.xp.lib_common_ui.base

interface IPresenter<in V : IView> {


    fun attachView(view: V)

    fun detach()
}