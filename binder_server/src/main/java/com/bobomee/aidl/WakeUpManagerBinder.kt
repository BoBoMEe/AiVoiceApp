package com.bobomee.aidl

import com.bobomee.aidl.callback.OnWakeUpResultListener
import com.bobomee.binder_server.wakeup.VoiceWakeUp

class WakeUpManagerBinder : IWakeUpManager.Stub() {
    override fun startWakeUp() {
        VoiceWakeUp.startWakeUp()
    }

    override fun stopWakeUp() {
        VoiceWakeUp.stopWakeUp()
    }

    override fun registerWakeUpListener(listener: IOnWakeUpResultListener?) {
        VoiceWakeUp.register(object : OnWakeUpResultListener() {
            override fun wakeUpReady() {
                listener?.wakeUpReady()
            }

            override fun wakeUpSuccess(result: String?) {
                listener?.wakeUpSuccess(result)
            }

            override fun wakeUpError(text: String?) {
                listener?.wakeUpError(text)
            }
        })
    }
}