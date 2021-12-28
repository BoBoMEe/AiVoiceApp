package com.bobomee.module_voice_setting.base

import android.app.Application
import com.bobomee.lib_base.base.app.IApp
import com.google.auto.service.AutoService

@AutoService(IApp::class)
class VoiceSettingApp : IApp {
    override fun initApp(app: Application) {

    }

}