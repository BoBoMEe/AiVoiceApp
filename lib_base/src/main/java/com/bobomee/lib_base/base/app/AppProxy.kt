package com.bobomee.lib_base.base.app

import android.app.Application
import java.util.*

class AppProxy : IApp {
    private var mLoader: ServiceLoader<IApp> =
        ServiceLoader.load(IApp::class.java, javaClass.classLoader)

    override fun initApp(app: Application) {
        mLoader.forEach { it.initApp(app) }
    }
}