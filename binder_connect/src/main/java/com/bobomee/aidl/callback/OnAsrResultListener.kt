package com.bobomee.aidl.callback

import com.bobomee.aidl.IOnAsrResultListener

open class OnAsrResultListener : IOnAsrResultListener.Stub() {
    override fun asrStartSpeak() {
    }

    override fun asrStopSpeak() {
    }

    override fun updateUserText(text: String?) {
    }

    override fun asrResult(result: String?) {
    }

    override fun asrVolume(volume: Int) {
    }
}