package com.xp.learn.mvp.presenter

import com.xp.learn.bean.Article
import com.xp.learn.bean.ArticleResponseBody
import com.xp.learn.bean.HttpResult
import com.xp.learn.ext.ss
import com.xp.learn.mvp.contract.HomeContract
import com.xp.learn.mvp.model.HomeModel
import com.xp.lib_common_ui.base.BasePresenter
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class HomePresenter : BasePresenter<HomeContract.Model, HomeContract.View>(),
    HomeContract.Presenter {


    override fun createModel(): HomeContract.Model? = HomeModel()

    override fun requestBanner() {


        mModel?.requestBanner()?.ss(mModel, mView, false) {
            mView?.setBanner(it.data)
        }


    }

    override fun requestHomeData() {
        requestBanner()
        Observable.zip(mModel?.getTopArticles(), mModel?.getArticles(0),
            BiFunction<HttpResult<MutableList<Article>>, HttpResult<ArticleResponseBody>
                    , HttpResult<ArticleResponseBody>> { t1, t2 ->
                t1.data.forEach {
                    it.top = true
                }
                t2.data.datas.addAll(0, t1.data)
                t2
            }

        ).ss(mModel, mView, false) {
            mView?.setArticles(it.data)
        }
    }

    override fun requestArticles(num: Int) {
        mModel?.getArticles(num)?.ss(mModel, mView, num == 0) {
            mView?.setArticles(it.data)
        }
    }

}