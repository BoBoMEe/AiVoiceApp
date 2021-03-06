package com.bobomee.module_setting

import android.text.Spannable
import android.text.Spanned
import android.text.TextPaint
import android.text.style.UnderlineSpan
import com.alibaba.android.arouter.facade.annotation.Route
import com.bobomee.lib_base.base.view.BaseActivity
import com.bobomee.lib_base.config.isBackJoke
import com.bobomee.lib_base.config.isHello
import com.bobomee.lib_base.config.setBackJoke
import com.bobomee.lib_base.config.setHello
import com.bobomee.lib_base.helper.ARouterHelper
import com.bobomee.lib_base.utils.SpUtils
import kotlinx.android.synthetic.main.activity_setting.*


/**
 * Profile: 系统设置
 */
@Route(path = ARouterHelper.PATH_SETTING)
class SettingActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }

    override fun getTitleText(): String {
        return title.toString()
    }

    override fun isShowBack(): Boolean {
        return true
    }

    override fun initView() {

        //欢迎词
        val isHello = SpUtils.isHello()
        mSwHello.isChecked = isHello

        mSwHello.setOnClickListener {
            !mSwHello.isChecked
            SpUtils.setHello(mSwHello.isChecked)
        }

        //笑话
        val isJoke = SpUtils.isBackJoke()
        mSwJokeBackground.isChecked = isJoke

        mSwJokeBackground.setOnClickListener {
            !mSwJokeBackground.isChecked
            SpUtils.setBackJoke(mSwJokeBackground.isChecked)
        }

        val version = packageManager.getPackageInfo(packageName, 0).versionName
        tvVersion.text = getString(R.string.text_version_ui, version)

        //去除下划线
        if (tvClassLink.text is Spannable) {
            val spannable = tvClassLink.text as Spannable
            spannable.setSpan(NoUnderlineSpan(), 0, spannable.length, Spanned.SPAN_MARK_MARK)
        }
    }

    class NoUnderlineSpan : UnderlineSpan() {
        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.color = ds.linkColor
            ds.isUnderlineText = false
        }
    }
}