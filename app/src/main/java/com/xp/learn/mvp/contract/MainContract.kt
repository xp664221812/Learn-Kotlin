package com.xp.learn.mvp.contract

import com.xp.learn.bean.HttpResult
import com.xp.learn.bean.UserInfoBody
import com.xp.lib_common_ui.base.IModel
import com.xp.lib_common_ui.base.IPresenter
import com.xp.lib_common_ui.base.IView
import io.reactivex.Observable

interface MainContract {
    interface View : IView {
        fun showUserInfo(userInfoBody: UserInfoBody?)
    }

    interface Presenter : IPresenter<View> {
        fun getUserInfo()
    }

    interface Model : IModel {
        fun getUserInfo(): Observable<HttpResult<UserInfoBody>>
    }
}