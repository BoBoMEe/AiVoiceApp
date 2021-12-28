package com.bobomee.module_weather.base

import android.app.Application
import com.bobomee.lib_base.base.app.IApp
import com.bobomee.module_weather.utils.AssetsUtils
import com.google.auto.service.AutoService

@AutoService(IApp::class)
class WeatherApp : IApp {
    override fun initApp(app: Application) {
        AssetsUtils.initUtils(app)
    }
}