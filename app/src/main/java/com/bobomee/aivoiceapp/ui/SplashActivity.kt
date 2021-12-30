package com.bobomee.aivoiceapp.ui

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.bobomee.aivoiceapp.R
import com.bobomee.lib_base.helper.ARouterHelper
import com.bobomee.lib_base.ktx.shortToast
import com.gyf.immersionbar.ImmersionBar

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
            startActivity()
            finish()
        }, 1500)
    }

    private fun startActivity() {
        when (applicationInfo.packageName) {
            ModuleConfig.MODULE_VOICE_APP -> ARouterHelper.startActivity(ARouterHelper.PATH_ASSISTANT)
            ModuleConfig.MODULE_ASSISTANT -> ARouterHelper.startActivity(ARouterHelper.PATH_ASSISTANT)
            ModuleConfig.MODULE_VOICE_SETTING -> ARouterHelper.startActivity(ARouterHelper.PATH_VOICE_SETTING)
            ModuleConfig.MODULE_DEVELOPER -> ARouterHelper.startActivity(ARouterHelper.PATH_DEVELOPER)
            ModuleConfig.MODULE_JOKE -> ARouterHelper.startActivity(ARouterHelper.PATH_JOKE)
            ModuleConfig.MODULE_MAP -> ARouterHelper.startActivity(ARouterHelper.PATH_MAP)
            ModuleConfig.MODULE_SETTING -> ARouterHelper.startActivity(ARouterHelper.PATH_SETTING)
            ModuleConfig.MODULE_WEATHER -> ARouterHelper.startActivity(ARouterHelper.PATH_WEATHER)
            else -> this.shortToast( "无法识别" + application.packageName)
        }
    }
}