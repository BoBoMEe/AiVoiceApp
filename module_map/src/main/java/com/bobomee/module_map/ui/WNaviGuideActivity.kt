package com.bobomee.module_map.ui

import android.app.Activity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.baidu.mapapi.walknavi.WalkNavigateHelper
import com.baidu.mapapi.walknavi.adapter.IWNaviStatusListener
import com.baidu.platform.comapi.walknavi.WalkNaviModeSwitchListener
import com.bobomee.lib_base.helper.ARouterHelper
import com.bobomee.lib_base.helper.VoiceHelper
import com.bobomee.lib_base.utils.LogUtils


/**
 * Profile: 线路导航
 */

@Route(path = ARouterHelper.PATH_MAP_NAVI)
class WNaviGuideActivity : Activity() {

    private lateinit var mNaviHelper: WalkNavigateHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mNaviHelper = WalkNavigateHelper.getInstance()
        val view = mNaviHelper.onCreate(this@WNaviGuideActivity)
        setContentView(view)
        mNaviHelper.startWalkNavi(this@WNaviGuideActivity)

        //TTS 没有走动，自己走一走
        mNaviHelper.setTTsPlayer { text, _ ->
            LogUtils.i("Navi TTS:$text")
            VoiceHelper.ttsStart(text)
            0
        }

        //AR切换
        mNaviHelper.setWalkNaviStatusListener(object : IWNaviStatusListener {
            override fun onWalkNaviModeChange(
                mode: Int,
                walkNaviModeSwitchListener: WalkNaviModeSwitchListener
            ) {
                mNaviHelper.switchWalkNaviMode(
                    this@WNaviGuideActivity,
                    mode,
                    walkNaviModeSwitchListener
                )
            }

            override fun onNaviExit() {

            }
        })
    }

    override fun onResume() {
        super.onResume()
        mNaviHelper.resume()
    }

    override fun onPause() {
        super.onPause()
        mNaviHelper.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mNaviHelper.quit()
    }
}