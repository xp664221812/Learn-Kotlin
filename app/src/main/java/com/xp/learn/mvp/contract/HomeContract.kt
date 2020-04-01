package com.xp.learn.mvp.contract

import com.xp.learn.bean.Article
import com.xp.learn.bean.ArticleResponseBody
import com.xp.learn.bean.Banner
import com.xp.learn.bean.HttpResult
import com.xp.lib_common_ui.base.IModel
import com.xp.lib_common_ui.base.IPresenter
import com.xp.lib_common_ui.base.IView
import io.reactivex.Observable

interface HomeContract {
    interface View : IView {
        fun setBanner(banners: List<Banner>?)
        fun setArticles(articles: ArticleResponseBody)
    }

    interface Presenter : IPresenter<View> {
        fun requestBanner()
        fun requestHomeData()
        fun requestArticles(num: Int)

    }

    interface Model : IModel {
        fun requestBanner(): Observable<HttpResult<MutableList<Banner>>>

        fun getArticles(num: Int): Observable<HttpResult<ArticleResponseBody>>

        fun getTopArticles(): Observable<HttpResult<MutableList<Article>>>
    }
}