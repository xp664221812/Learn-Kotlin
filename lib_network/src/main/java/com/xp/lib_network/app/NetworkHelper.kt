package com.xp.lib_network.app

import android.content.Context

object NetworkHelper {
    @JvmField
    var mContext: Context? = null

    @JvmStatic
    public fun initContext(context: Context) {
        mContext = context
    }

    @JvmStatic
    public fun getContext(): Context? {
        return mContext
    }

}