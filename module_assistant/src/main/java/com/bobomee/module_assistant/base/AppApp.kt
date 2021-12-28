package com.bobomee.module_assistant.base

import android.app.Application
import com.bobomee.module_assistant.words.WordsTools
import com.bobomee.lib_base.base.app.IApp
import com.google.auto.service.AutoService

@AutoService(IApp::class)
class AppApp : IApp {
    override fun initApp(app: Application) {
        //初始化语音服务
        WordsTools.initTools(app)
    }
}