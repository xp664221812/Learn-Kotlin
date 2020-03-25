package com.xp.learn.mvp.model

import com.xp.learn.api.Api
import com.xp.learn.bean.HttpResult
import com.xp.learn.bean.LoginData
import com.xp.learn.mvp.contract.LoginContract
import com.xp.lib_network.RetrofitHelper
import io.reactivex.Observable

class LoginModel : LoginContract.Model{
    override fun login(userName: String, password: String): Observable<HttpResult<LoginData>> {
        return RetrofitHelper.getRetrofit()?.create(Api::class.java)!!.login(userName,password)
    }


}