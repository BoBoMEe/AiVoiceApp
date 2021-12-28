package com.bobomee.module_map.base

import android.app.Application
import com.baidu.mapapi.SDKInitializer
import com.bobomee.lib_base.base.app.IApp
import com.bobomee.module_map.tools.MapManager
import com.google.auto.service.AutoService

@AutoService(IApp::class)
class MapApp : IApp {

    override fun initApp(app: Application) {
        SDKInitializer.initialize(app)
        MapManager.initMapManager(app)
    }
}