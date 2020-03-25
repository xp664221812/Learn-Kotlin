package com.xp.learn.api

import com.xp.learn.bean.*
import io.reactivex.Observable
import retrofit2.http.*

interface Api {


    @FormUrlEncoded
    @POST("/user/login")
    fun login(
        @Field("username") username: String?, @Field(
            "password"
        ) password: String?
    ): Observable<HttpResult<LoginData>>


    @GET("/banner/json")
    fun requestBanner(): Observable<HttpResult<MutableList<Banner>>>


    @GET("/article/top/json")
    fun requestTopArticles(): Observable<HttpResult<MutableList<Article>>>


    @GET("/article/list/{num}/json")
    fun getArticles(@Path("num") num: Int): Observable<HttpResult<ArticleResponseBody>>


    @GET("/lg/coin/userinfo/json")
    fun getUserInfo(): Observable<HttpResult<UserInfoBody>>

}