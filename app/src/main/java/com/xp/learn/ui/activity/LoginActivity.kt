package com.xp.learn.ui.activity

import android.text.TextUtils
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.xp.learn.R
import com.xp.learn.bean.HttpResult
import com.xp.learn.bean.LoginData
import com.xp.learn.constant.Constant
import com.xp.learn.event.LoginEvent
import com.xp.learn.mvp.contract.LoginContract
import com.xp.learn.mvp.presenter.LoginPresenter
import com.xp.lib_common_ui.base.BaseMVPActivity
import com.xp.lib_common_ui.utils.DialogUtil
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.EventBus

class LoginActivity : BaseMVPActivity<LoginContract.View, LoginContract.Presenter>(),
    LoginContract.View {
    override fun createPresenter(): LoginContract.Presenter? = LoginPresenter()

    override fun initLayout(): Int = R.layout.activity_login

    private val progressDialog by lazy {
        DialogUtil.getWaitingDialog(this, "正在登陆")
    }

    override fun initData() {
    }

    override fun initListeners() {
        bt_login.setOnClickListener(onLoginClickListener)
    }

    override fun loginSuccess(httpResult: HttpResult<LoginData>) {
        if (httpResult.data == null) {
            showToast("账号或密码错误")
        } else {
            val loginData: LoginData = httpResult.data
            SPUtils.getInstance().put(Constant.ISLOGIN, true)
            SPUtils.getInstance().put(Constant.USERNAME, loginData.username)
            SPUtils.getInstance().put(Constant.TOKEN, loginData.token)
            EventBus.getDefault().post(LoginEvent(true))
            finish()
        }
//        SPUtils.getInstance().put(Constant.ISLOGIN, true)
    }

    override fun showLoading() {
        progressDialog.show()
    }

    override fun hideLoading() {
        progressDialog.dismiss()
    }

    private var onLoginClickListener = View.OnClickListener {
        val userName = et_username.text.toString()
        val password = et_password.text.toString()
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
            showToast("账号或密码不能为空")
            return@OnClickListener
        }
        showLoading()
        mPresenter?.login(userName, password)
    }

}