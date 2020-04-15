package com.xp.learn.app

import android.app.Application
import android.content.Context
import com.xp.lib_network.app.NetworkHelper

class App : Application() {


    companion object {
        var context: Context? = null

    }


    override fun onCreate() {
        super.onCreate()
        context = this

        NetworkHelper.initContext(this)

    }


}