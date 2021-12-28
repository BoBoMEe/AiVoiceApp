package com.bobomee.aidl

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.bobomee.binder_server.asr.VoiceAsr
import com.bobomee.binder_server.tts.VoiceTTS
import com.bobomee.binder_server.wakeup.VoiceWakeUp
import com.bobomee.binder_connect.ServiceTag
import com.bobomee.lib_base.helper.NotificationHelper

class BinderPoolService : Service() {
    private val mBinder by lazy { PoolBinder(this) }
    override fun onBind(intent: Intent?): IBinder {
        return mBinder
    }

    class PoolBinder(private val context: Context) : IBinderPool.Stub() {
        override fun queryBinder(tag: String?): IBinder? {
            return when (tag) {
                ServiceTag.ASR -> AsrManagerBinder()
                ServiceTag.TTS -> TTSManagerBinder()
                ServiceTag.WAKE_UP -> WakeUpManagerBinder()
                else -> null
            }
        }

    }

    override fun onCreate() {
        super.onCreate()
        VoiceAsr.initAsr(this)
        VoiceTTS.initTTS(this)
        VoiceWakeUp.initWakeUp(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        bindNotification()
        return START_STICKY_COMPATIBILITY
    }

    //绑定通知栏
    private fun bindNotification() {
        startForeground(
            11111111,
            NotificationHelper.bindPoolService()
        )
    }
}