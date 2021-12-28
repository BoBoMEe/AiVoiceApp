package com.bobomee.aidl.callback

import com.bobomee.aidl.IOnWakeUpResultListener

open class OnWakeUpResultListener : IOnWakeUpResultListener.Stub() {
    override fun wakeUpReady() {
    }

    override fun wakeUpSuccess(result: String?) {
    }

    override fun wakeUpError(text: String?) {
    }
}