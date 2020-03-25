package com.xp.learn.ui.activity

import android.view.Gravity
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.FragmentTransaction
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.SizeUtils
import com.xp.learn.R
import com.xp.learn.bean.UserInfoBody
import com.xp.learn.constant.Constant
import com.xp.learn.event.LoginEvent
import com.xp.learn.mvp.contract.MainContract
import com.xp.learn.mvp.presenter.MainPresenter
import com.xp.learn.ui.fragment.HomeFragment
import com.xp.lib_common_ui.base.BaseMVPActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_toolbar.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : BaseMVPActivity<MainContract.View, MainContract.Presenter>(),
    MainContract.View {


    lateinit var toggle: ActionBarDrawerToggle

    private var homeFragment: HomeFragment? = null


    private var mIndex = 0


    //只在headView第一次被访问才会执行，findViewById也可以这么初始化
    private val headView by lazy {
        navigationView.getHeaderView(0)

    }

    private val userName by lazy {
        headView.findViewById<TextView?>(R.id.tv_name)
    }

    private val userInfo by lazy {
        headView.findViewById<TextView?>(R.id.tv_user_info)
    }

    private val mScore by lazy {
        MenuItemCompat.getActionView(navigationView.menu.findItem(R.id.point)) as TextView
    }


    override fun initLayout(): Int {
        return R.layout.activity_main
    }

    override fun initViews() {
        super.initViews()
        mScore.run {
            mScore.gravity = Gravity.CENTER_VERTICAL
            mScore.textSize = 12f
            mScore.setTextColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.text_color
                )
            )
        }
        showFragment(mIndex)

    }

    private fun showFragment(index: Int) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        hideFragment(fragmentTransaction)
        when (mIndex) {
            0 ->
                if (homeFragment == null) {
                    homeFragment = HomeFragment.getInstance()
                    fragmentTransaction.add(R.id.container, homeFragment!!, "home")
                } else {
                    fragmentTransaction.show(homeFragment!!)
                }

        }
        fragmentTransaction.commit()
    }


    private fun hideFragment(transaction: FragmentTransaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment!!)
        }
    }


    override fun initData() {
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }

        toggle = ActionBarDrawerToggle(
            this,
            drawer,
            R.string.global_action_open,
            R.string.global_action_close
        )
        toggle.syncState()
        drawer.addDrawerListener(toggle)

        initNavigationView()

        initNavigationBottomView()


        if (SPUtils.getInstance().getBoolean(Constant.ISLOGIN)) {
            userName?.text = SPUtils.getInstance().getString(Constant.USERNAME)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (toggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }


    override fun onStart() {
        super.onStart()
        mPresenter?.getUserInfo()
    }

    private fun initNavigationView() {
        navigationView.setNavigationItemSelectedListener {
            drawer.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }
    }

    private fun initNavigationBottomView() {

        bottom_navigation.run {
            setSmallTextSize(12f)
            setLargeTextSize(12f)
            setIconSize(22f, 22f)
            setIconsMarginTop(SizeUtils.dp2px(8f))
            itemIconTintList = null
            enableShiftingMode(false)
            enableItemShiftingMode(false)
            enableAnimation(false)
        }

    }

    override fun initListeners() {
    }

    override fun showUserInfo(bean: UserInfoBody) {
        if (bean == null) {
            return
        }
        mScore.text = java.lang.String.valueOf(bean.coinCount)
        val level: String = (bean.coinCount / 100 + 1).toString() + ""
        val msg = java.lang.String.format(
            getString(R.string.level_and_rank),
            level,
            bean.rank.toString() + ""
        )
        userInfo?.text = msg
    }


    override fun showLoading() {
        super.showLoading()
    }

    override fun hideLoading() {

    }

    override fun createPresenter(): MainContract.Presenter? {
        return MainPresenter()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventLogin(loginEvent: LoginEvent) {
        if (loginEvent.isLogin) {
            userName?.text = SPUtils.getInstance().getString(Constant.USERNAME)
            showToast("登陆成功了")
        }
    }


    override fun useEventBus() = true

    /**
     * 伴生对象，每个class都有一个半生对象
     */
    companion object {
        val TAG = MainActivity::class.simpleName

    }

}
