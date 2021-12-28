package com.bobomee.aidl.callback

import com.bobomee.aidl.IOnTTSResultListener

open class OnTTSResultListener : IOnTTSResultListener.Stub() {
    override fun onSpeechStart(var1: String?) {
    }

    override fun onSpeechProgressChanged(var1: String?, var2: Int) {
    }

    override fun onSpeechFinish(var1: String?) {
    }

    override fun onError(var1: String?) {
    }
}