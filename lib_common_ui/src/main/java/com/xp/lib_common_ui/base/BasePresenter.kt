package com.xp.lib_common_ui.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner


abstract class BasePresenter<M : IModel, V : IView> : IPresenter<V>, LifecycleObserver {

    protected var mView: V? = null
    protected var mModel: M? = null


    override fun attachView(view: V) {
        this.mView = view

        mModel = createModel()
        /*if (mView is LifecycleOwner) {
            (mView as LifecycleOwner).lifecycle.addObserver(this)

        }*/
    }

    open fun createModel(): M? = null

    override fun detach() {
//        mModel?.onDetach()
        mModel = null
        mView = null
    }

}