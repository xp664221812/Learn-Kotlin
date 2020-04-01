package com.xp.learn.ui.fragment

import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.bingoogolapple.bgabanner.BGABanner
import com.blankj.utilcode.util.SPUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.xp.learn.R
import com.xp.learn.adapter.HomeAdapter
import com.xp.learn.bean.Article
import com.xp.learn.bean.ArticleResponseBody
import com.xp.learn.bean.Banner
import com.xp.learn.constant.Constant
import com.xp.learn.mvp.contract.HomeContract
import com.xp.learn.mvp.presenter.HomePresenter
import com.xp.learn.ui.activity.ContentActivity
import com.xp.learn.ui.activity.LoginActivity
import com.xp.lib_common_ui.base.BaseMVPFragment
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_refresh_layout.*
import kotlinx.android.synthetic.main.item_home_banner.view.*

class HomeFragment : BaseMVPFragment<HomeContract.View, HomeContract.Presenter>(),
    HomeContract.View {
    lateinit var banners: ArrayList<Banner>

    lateinit var bannerView: View

    var data = mutableListOf<Article>()

    var currentPage = 1

    var isRefresh = true

    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(activity, data)
    }

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }


    private val dividerItemDecoration: DividerItemDecoration by lazy {
        DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
    }

    override fun createPresenter(): HomeContract.Presenter? {
        return HomePresenter()
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_refresh_layout
    }

    override fun initViews() {
        super.initViews()
        bannerView = layoutInflater.inflate(R.layout.item_home_banner, null)

        recyclerView.run {
            layoutManager = linearLayoutManager
            adapter = homeAdapter
            itemAnimator = DefaultItemAnimator()
            activity?.getDrawable(R.color.gray)?.let { dividerItemDecoration.setDrawable(it) }
            addItemDecoration(dividerItemDecoration)
        }

        homeAdapter.run {
            bindToRecyclerView(recyclerView)
            setOnLoadMoreListener(loadMoreListener, recyclerView)
            onItemChildClickListener = this@HomeFragment.onItemChildClickListener
            onItemClickListener = this@HomeFragment.onItemClickListener

            addHeaderView(bannerView)
        }

        swipeRefreshLayout.setOnRefreshListener(refreshListener)

    }

    override fun lazyLoad() {
        mPresenter?.requestHomeData()
    }

    override fun setBanner(list: List<Banner>?) {
        banners = list as ArrayList<Banner>
        var pathList = ArrayList<String>()
        var titleList = ArrayList<String>()

        Observable.fromIterable(banners).subscribe {
            pathList.add(it.imagePath)
            titleList.add(it.title)
        }

        bannerView.banner.run {
            setAutoPlayAble(banners.size > 1)
            setData(pathList, titleList)
            setAdapter(bannerAdapter)
        }


    }

    override fun setArticles(body: ArticleResponseBody) {
        currentPage = body.curPage
        if (isRefresh) {
            homeAdapter.replaceData(body.datas)
        } else {
            homeAdapter.addData(body.datas)
        }

        if (currentPage < body.pageCount) {
            homeAdapter.loadMoreComplete()
        } else {
            homeAdapter.loadMoreEnd()
        }
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
        swipeRefreshLayout.isRefreshing = false
        if (isRefresh) {
            homeAdapter.setEnableLoadMore(true)
        }
    }

    private val bannerAdapter: BGABanner.Adapter<ImageView, String> by lazy {
        BGABanner.Adapter<ImageView, String> { _, itemView, model, position ->
            Glide.with(itemView.context).load(model).into(itemView)
        }
    }

    private val loadMoreListener: BaseQuickAdapter.RequestLoadMoreListener =
        BaseQuickAdapter.RequestLoadMoreListener {
            isRefresh = false
            swipeRefreshLayout.isRefreshing = false
            mPresenter?.requestArticles(currentPage)
        }

    private val refreshListener = SwipeRefreshLayout.OnRefreshListener {
        isRefresh = true
        homeAdapter.setEnableLoadMore(false)
        mPresenter?.requestHomeData()
    }

    private val onItemClickListener =
        BaseQuickAdapter.OnItemClickListener { _, _, position ->
            val article = homeAdapter.getItem(position)
            val intent = Intent(activity, ContentActivity::class.java)
            intent.putExtra("link", article?.link)
            startActivity(intent)

        }


    private val onItemChildClickListener =
        BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.collect -> {
                    var article = homeAdapter.getItem(position)
                    if (SPUtils.getInstance().getBoolean(Constant.ISLOGIN)) {

                    } else {
                        Intent(activity, LoginActivity::class.java).run {
                            startActivity(this)
                            showToast("请先登录")
                        }
                    }
                }
            }

        }

    companion object {
        fun getInstance(): HomeFragment = HomeFragment()
    }

}