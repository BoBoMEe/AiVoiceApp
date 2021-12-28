package com.bobomee.aidl

import com.bobomee.aidl.callback.OnAsrResultListener
import com.bobomee.aidl.callback.OnNluResultListener
import com.bobomee.binder_server.asr.VoiceAsr

class AsrManagerBinder : IAsrManager.Stub() {
    override fun startAsr() {
        VoiceAsr.startAsr()
    }

    override fun stopAsr() {
        VoiceAsr.stopAsr()
    }

    override fun cancelAsr() {
        VoiceAsr.cancelAsr()
    }

    override fun releaseAsr() {
        VoiceAsr.releaseAsr()
    }

    override fun registerAsrListener(listener: IOnAsrResultListener?) {
        VoiceAsr.register(object : OnAsrResultListener() {
            override fun asrStartSpeak() {
                listener?.asrStartSpeak()
            }

            override fun asrStopSpeak() {
                listener?.asrStopSpeak()
            }

            override fun updateUserText(text: String?) {
                listener?.updateUserText(text)
            }

            override fun asrResult(result: String?) {
                listener?.asrResult(result)
            }

            override fun asrVolume(volume: Int) {
                listener?.asrVolume(volume)
            }
        })
    }

    override fun registerNluListener(listener: IOnNluResultListener?) {
        VoiceAsr.register(object : OnNluResultListener() {
            override fun nluResult(nlu: String?) {
                listener?.nluResult(nlu)
            }
        })
    }
}