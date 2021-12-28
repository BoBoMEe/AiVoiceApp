package com.bobomee.aidl.activity

import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bobomee.binder_connect.ServiceTools

class TranslucentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startServiceWrapper()
        finish()
    }

    private fun startServiceWrapper() {
        val intent = Intent()
        val servicePkg = ServiceTools.getIntentPkg()
        val serviceClz = ServiceTools.getServiceClz()
        intent.component = ComponentName(servicePkg,serviceClz)
        intent.action = ServiceTools.getServiceAct()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }
}