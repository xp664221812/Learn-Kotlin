package com.xp.learn.adapter

import android.content.Context
import android.text.TextUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xp.learn.R
import com.xp.learn.bean.Article
import com.xp.learn.bean.Tag

class HomeAdapter(var context: Context?, data: MutableList<Article>?) :
    BaseQuickAdapter<Article, BaseViewHolder>(R.layout.item_home_list, data) {


    override fun convert(helper: BaseViewHolder?, article: Article?) {
        helper ?: return
        article ?: return
        helper.setGone(R.id.fresh, article.fresh)

        helper.setGone(R.id.top, article.top)

        val tags: List<Tag> = article.tags
        helper.setGone(R.id.tag, tags.isNotEmpty())
        if (tags.isNotEmpty()) {
            helper.setText(R.id.tag, article.tags[0].name)
        }

        helper.setText(R.id.title, article.title)

        helper.setText(R.id.date, article.niceDate)

        var author: String = article.author
        if (TextUtils.isEmpty(author)) {
            author = article.shareUser
        }
        helper.setText(R.id.author, author)

        val chapterName: String =
            article.superChapterName + " / " + article.chapterName
        helper.setText(R.id.chapterName, chapterName)

        if (article.collect) {
            helper.setImageResource(R.id.collect, R.mipmap.ic_like)
        } else {
            helper.setImageResource(R.id.collect, R.mipmap.ic_unlike)
        }

        helper.addOnClickListener(R.id.collect)

    }

}