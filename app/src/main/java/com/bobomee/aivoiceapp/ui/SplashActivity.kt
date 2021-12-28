package com.bobomee.aivoiceapp.ui

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar
import com.bobomee.aivoiceapp.R
import com.bobomee.lib_base.helper.ARouterHelper

/**
 * Profile: 启动页
 */
class SplashActivity : AppCompatActivity() {

    //倒计时
    private val mHandler by lazy { Handler() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //状态栏
        ImmersionBar.with(this).transparentStatusBar().init()

        mHandler.postDelayed({
            ARouterHelper.startActivity(ARouterHelper.PATH_ASSISTANT)
            finish()
        }, 1500)
    }
}