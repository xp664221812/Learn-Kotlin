package com.xp.learn.ui.activity

import android.content.Intent
import android.net.Uri
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.xp.learn.R
import com.xp.learn.mvp.contract.ContentContract
import com.xp.learn.mvp.presenter.ContentPresenter
import com.xp.lib_common_ui.base.BaseMVPActivity
import kotlinx.android.synthetic.main.activity_content.*
import kotlinx.android.synthetic.main.common_toolbar.*
import kotlinx.android.synthetic.main.common_toolbar_back_view.*
import java.net.URISyntaxException

class ContentActivity : BaseMVPActivity<ContentContract.View, ContentContract.Presenter>(),
    ContentContract.View {
    override fun createPresenter(): ContentContract.Presenter? = ContentPresenter()

    override fun initLayout(): Int = R.layout.activity_content

    private val link: String by lazy {
        intent.getStringExtra("link")
    }

    private val title: String by lazy {
        intent.getStringExtra("title")
    }


    override fun initData() {
        webView.loadUrl(link)

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                try { //处理intent协议
                    if (url.startsWith("intent://")) {
                        val intent: Intent
                        try {
                            intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                            intent.addCategory("android.intent.category.BROWSABLE")
                            intent.component = null
                            intent.selector = null
                            val resolves =
                                packageManager.queryIntentActivities(intent, 0)
                            if (resolves.size > 0) {
                                startActivityIfNeeded(intent, -1)
                            }
                            return true
                        } catch (e: URISyntaxException) {
                            e.printStackTrace()
                        }
                    }
                    // 处理自定义scheme协议
                    if (!url.startsWith("http")) {
                        LogUtils.d("处理自定义scheme-->$url")
                        try { // 以下固定写法
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(url)
                            )
                            intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK
                                    or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                            startActivity(intent)
                        } catch (e: Exception) { // 防止没有安装的情况
                            e.printStackTrace()
                            ToastUtils.showShort("您所打开的第三方App未安装！")
                        }
                        return true
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return super.shouldOverrideUrlLoading(view, url)
            }
        }
//        webView.setWebChromeClient(new WebChromeClient());


        //        webView.setWebChromeClient(new WebChromeClient());
        webView.settings.javaScriptEnabled = true


        webView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN

        webView.settings.loadWithOverviewMode = true

        //设置可以支持缩放

        //设置可以支持缩放
        webView.settings.setSupportZoom(true)

        //扩大比例的缩放

        //扩大比例的缩放
        webView.settings.useWideViewPort = true

        //设置是否出现缩放工具

        //设置是否出现缩放工具
        webView.settings.builtInZoomControls = true

        webView.settings.domStorageEnabled = true
        tv_title?.text = title
        tv_title?.isSelected = true

    }

    override fun initListeners() {
        toolbar.setOnClickListener {
            onBackPressed()
        }


    }

    override fun hideLoading() {
    }

}