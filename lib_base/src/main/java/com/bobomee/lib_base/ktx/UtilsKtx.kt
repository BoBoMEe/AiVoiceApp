package com.bobomee.lib_base.ktx

import android.app.Activity
import android.app.Service
import android.content.Context
import android.graphics.Point
import android.view.WindowManager
import android.widget.Toast

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

fun Activity.shortToast(text:CharSequence){
    Toast.makeText(this,text,Toast.LENGTH_SHORT).show()
}