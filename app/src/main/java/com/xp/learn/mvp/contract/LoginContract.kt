package com.xp.learn.mvp.contract

import com.xp.learn.bean.HttpResult
import com.xp.learn.bean.LoginData
import com.xp.lib_common_ui.base.IModel
import com.xp.lib_common_ui.base.IPresenter
import com.xp.lib_common_ui.base.IView
import io.reactivex.Observable

interface LoginContract {
    interface View : IView {
        fun loginSuccess(httpResult: HttpResult<LoginData>)
    }

    interface Presenter : IPresenter<View> {
        fun login(userName: String, password: String)
    }

    interface Model : IModel {
        fun login(userName: String, password: String): Observable<HttpResult<LoginData>>
    }
}