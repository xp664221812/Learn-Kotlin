package com.xp.learn.ext

import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.SPUtils
import com.xp.learn.bean.BaseBean
import com.xp.learn.constant.Constant
import com.xp.lib_common_ui.base.IModel
import com.xp.lib_common_ui.base.IView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun <T : BaseBean> Observable<T>.ss(
    model: IModel?,
    view: IView?,
    isShowLoading: Boolean = true,
    onSuccess: (T) -> Unit
) {
    this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(object : Observer<T> {
            override fun onComplete() {
                view?.hideLoading()
            }

            override fun onSubscribe(d: Disposable) {
                if (isShowLoading) {
                    view?.showLoading()
                }
                if (!NetworkUtils.isConnected()) {
                    onComplete()
                }
            }

            override fun onNext(t: T) {
                when (t.errCode) {
                    0 -> onSuccess(t)
                    -1001 -> {
                        //token过期了
                        view?.showToast("检测到您未登录，请登录之后再操作")
                        SPUtils.getInstance().put(Constant.ISLOGIN, false)

                    }
                    else -> {
                        view?.showError(t.errorMsg)
                    }


                }

            }

            override fun onError(e: Throwable) {
                view?.showError(e.message!!)
            }

        })
}