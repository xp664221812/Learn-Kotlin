package com.xp.learn.mvp.model

import com.xp.learn.api.Api
import com.xp.learn.bean.HttpResult
import com.xp.learn.bean.UserInfoBody
import com.xp.learn.mvp.contract.MainContract
import com.xp.lib_network.RetrofitHelper
import io.reactivex.Observable

class MainModel : MainContract.Model{
    override fun getUserInfo(): Observable<HttpResult<UserInfoBody>> {
        return RetrofitHelper.getRetrofit()?.create(Api::class.java)!!.getUserInfo()

    }


}