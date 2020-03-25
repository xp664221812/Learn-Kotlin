package com.xp.learn.mvp.presenter

import com.xp.learn.ext.ss
import com.xp.learn.mvp.contract.LoginContract
import com.xp.learn.mvp.contract.MainContract
import com.xp.learn.mvp.model.LoginModel
import com.xp.learn.mvp.model.MainModel
import com.xp.lib_common_ui.base.BasePresenter

class LoginPresenter : BasePresenter<LoginContract.Model, LoginContract.View>(),
    LoginContract.Presenter {


    override fun createModel(): LoginContract.Model? {
        return LoginModel()
    }

    override fun login(userName: String, password: String) {
        mModel?.login(userName, password)?.ss(mModel, mView) {
            mView?.loginSuccess(it)
        }
    }

}