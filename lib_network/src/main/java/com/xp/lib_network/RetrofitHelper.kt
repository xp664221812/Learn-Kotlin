package com.xp.lib_network

import com.blankj.utilcode.util.LogUtils
import com.xp.lib_network.cookie.MyCookieJarImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private var retrofit: Retrofit? = null

    const val BASE_URL = "https://www.wanandroid.com/"

    public fun getRetrofit(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .client(getOKHTTPClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }

        return retrofit
    }

    private fun getOKHTTPClient(): OkHttpClient {

        var builder = OkHttpClient.Builder()
        var logIntercept = HttpLoggingInterceptor {
            LogUtils.d(it)
        }

        if (BuildConfig.DEBUG) {
            logIntercept.level = HttpLoggingInterceptor.Level.BASIC
        } else {
            logIntercept.level = HttpLoggingInterceptor.Level.NONE
        }

        builder.run {
            addInterceptor(logIntercept)
            cookieJar(MyCookieJarImpl())
        }

        return builder.build()
    }


}