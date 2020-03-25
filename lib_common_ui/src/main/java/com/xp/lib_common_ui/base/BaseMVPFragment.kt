package com.xp.lib_common_ui.base

import com.blankj.utilcode.util.ToastUtils

abstract class BaseMVPFragment<in V : IView, P : IPresenter<V>> : BaseFragment(), IView {


    protected var mPresenter: P? = null

    protected abstract fun createPresenter(): P?


    override fun initViews() {
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detach()
        mPresenter = null
    }

    override fun showError(err: String) {
        ToastUtils.showShort(err)
    }

    override fun showMsg(msg: String) {
        ToastUtils.showShort(msg)
    }
}