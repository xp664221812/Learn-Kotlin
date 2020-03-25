package com.xp.lib_common_ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xp.lib_common_ui.utils.StatusBarUtil
import org.greenrobot.eventbus.EventBus

abstract class BaseActivity : AppCompatActivity() {

    /*   var toolbar: Toolbar = lazy {
           findViewById(R.id.toolbar)
       }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initLayout())
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }

        initViews()
        initData()
        initListeners()
    }


    override fun onResume() {
        super.onResume()
//        StatusBarUtil.statusBarLightMode(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)

        }
    }

    abstract fun initLayout(): Int

    abstract fun initViews()

    abstract fun initData()

    abstract fun initListeners()

    open fun useEventBus(): Boolean = false

}