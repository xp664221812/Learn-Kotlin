package com.xp.lib_common_ui.base

import com.blankj.utilcode.util.ToastUtils

abstract class BaseMVPActivity<in V : IView, P : IPresenter<V>> : BaseActivity(), IView {
    protected var mPresenter: P? = null

    override fun initViews() {
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detach()
        mPresenter = null
    }

    abstract fun createPresenter(): P?

    override fun showLoading() {

    }

    override fun showError(err: String) {
        ToastUtils.showShort(err)
    }

    override fun showMsg(msg: String) {
        ToastUtils.showShort(msg)
    }


}