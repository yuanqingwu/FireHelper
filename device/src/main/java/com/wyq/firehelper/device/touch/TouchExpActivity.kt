package com.wyq.firehelper.device.touch

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.wyq.firehelper.article.ArticleConstants
import com.wyq.firehelper.article.base.BaseCaseActivity
import com.wyq.firehelper.device.databinding.ActivityTouchExpBinding

/**
 * touch 事件传递实验
 */
class TouchExpActivity : BaseCaseActivity() {

    override fun inflateViewBinding(layoutInflater: LayoutInflater): ViewBinding {
        return ActivityTouchExpBinding.inflate(layoutInflater)
    }

    override fun initView() {

    }

    override fun getToolBarTitle(): String {
        return "touch"
    }

    override fun getArticleList(): MutableList<Any?> {
        return ArticleConstants.getListByFilter("touch").toMutableList()
    }
}