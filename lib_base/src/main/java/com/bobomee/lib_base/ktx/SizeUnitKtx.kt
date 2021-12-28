package com.bobomee.lib_base.ktx

import android.app.Service
import android.content.Context
import android.graphics.Point
import android.view.WindowManager

fun Context.screenHeight(): Int {
    return screenPoint().y
}

fun Context.screenPoint(): Point {
    return Point().apply {
        val windowManager = getSystemService(Service.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        display.getRealSize(this)
    }
}