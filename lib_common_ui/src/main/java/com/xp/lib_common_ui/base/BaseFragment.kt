package com.xp.lib_common_ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import org.greenrobot.eventbus.EventBus

abstract class BaseFragment : Fragment() {

    private var isViewPrepared = false

    private var hasLoaded = false


    @LayoutRes
    abstract fun getLayoutResId(): Int

    abstract fun initViews()

    abstract fun lazyLoad()

    open fun useEventBus() = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater?.inflate(getLayoutResId(), null)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }
        isViewPrepared = true
        initViews()
        lazyLoadDataIfPrepared()

    }

    override fun onDestroy() {
        super.onDestroy()
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
    }


    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isViewPrepared && !hasLoaded) {
            lazyLoad()
            hasLoaded = true
        }
    }

}