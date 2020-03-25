package com.xp.learn.mvp.model

import com.xp.learn.api.Api
import com.xp.learn.bean.Article
import com.xp.learn.bean.ArticleResponseBody
import com.xp.learn.bean.Banner
import com.xp.learn.bean.HttpResult
import com.xp.learn.mvp.contract.HomeContract
import com.xp.lib_network.RetrofitHelper
import io.reactivex.Observable

class HomeModel : HomeContract.Model {
    override fun requestBanner(): Observable<HttpResult<MutableList<Banner>>> {
        return RetrofitHelper.getRetrofit()?.create(Api::class.java)!!.requestBanner()
    }

    override fun getArticles(num: Int): Observable<HttpResult<ArticleResponseBody>> {
        return RetrofitHelper.getRetrofit()?.create(Api::class.java)!!.getArticles(num)
    }

    override fun getTopArticles(): Observable<HttpResult<MutableList<Article>>> {
        return RetrofitHelper.getRetrofit()?.create(Api::class.java)!!.requestTopArticles()
    }


}