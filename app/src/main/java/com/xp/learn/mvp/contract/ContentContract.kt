package com.xp.learn.mvp.contract

import com.xp.lib_common_ui.base.IModel
import com.xp.lib_common_ui.base.IPresenter
import com.xp.lib_common_ui.base.IView

interface ContentContract {
    interface View : IView {
    }

    interface Presenter : IPresenter<View> {
    }

    interface Model : IModel {
    }
}