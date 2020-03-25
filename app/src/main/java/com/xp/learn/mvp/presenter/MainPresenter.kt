package com.xp.learn.mvp.presenter

import com.xp.learn.ext.ss
import com.xp.learn.mvp.contract.MainContract
import com.xp.learn.mvp.model.MainModel
import com.xp.lib_common_ui.base.BasePresenter

class MainPresenter : BasePresenter<MainContract.Model, MainContract.View>(),
    MainContract.Presenter {


    override fun createModel(): MainContract.Model? {
        return MainModel()
    }

    override fun getUserInfo() {
        mModel?.getUserInfo()?.ss(mModel, mView) {
            mView?.showUserInfo(it.data)
        }
    }

}