package com.bobomee.lib_base.base.app

import android.app.Application
import android.text.TextUtils
import com.bobomee.binder_connect.ServiceTools
import com.bobomee.lib_base.helper.ARouterHelper
import com.bobomee.lib_base.helper.CommonSettingHelper
import com.bobomee.lib_base.helper.NotificationHelper
import com.bobomee.lib_base.utils.CommonUtils
import com.bobomee.lib_base.utils.SoundPoolUtils
import com.bobomee.lib_base.utils.SpUtils

/**
 * Profile: 基类App
 */
class BaseApp : Application() {

    private val mLoadProxy by lazy(mode = LazyThreadSafetyMode.NONE) { AppProxy() }

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(ActivityLifecycleCallbacksImpl())
        //只有主进程才能初始化
        val processName = CommonUtils.getProcessName(this)
        if (!TextUtils.isEmpty(processName)) {
            if (processName == packageName) {
                initApp()
            }
        }
    }

    //初始化App
    fun initApp() {
        ARouterHelper.initHelper(this)
        NotificationHelper.initHelper(this)
        SoundPoolUtils.initUtils(this)
        SpUtils.initUtils(this)
        CommonSettingHelper.initHelper(this)
        ServiceTools.pckage { "com.bobomee.aivoiceapp" }
            .activity({ "com.bobomee.aidl.activity.TranslucentActivity" })
            .service({ "com.bobomee.aidl.BinderPoolService" }).init(this)
        ServiceTools.bind()
        mLoadProxy.initApp(this)
    }
}