package com.bobomee.aidl

import com.bobomee.aidl.callback.OnTTSResultListener
import com.bobomee.binder_server.tts.VoiceTTS

class TTSManagerBinder : ITTSManager.Stub() {
    override fun setPeople(people: String?) {
        VoiceTTS.setPeople(people ?: "")
    }

    override fun setVoiceSpeed(speed: String?) {
        VoiceTTS.setVoiceSpeed(speed ?: "")
    }

    override fun setVoiceVolume(volume: String?) {
        VoiceTTS.setVoiceVolume(volume ?: "")
    }

    override fun ttsStart(text: String?) {
        VoiceTTS.start(text ?: "")
    }

    override fun ttsPause() {
        VoiceTTS.pause()
    }

    override fun ttsResume() {
        VoiceTTS.resume()
    }

    override fun ttsStop() {
        VoiceTTS.stop()
    }

    override fun ttsRelease() {
        VoiceTTS.release()
    }

    override fun registerTTSListener(listener: IOnTTSResultListener?) {
        VoiceTTS.register(object : OnTTSResultListener() {
            override fun onSpeechStart(var1: String?) {
                listener?.onSpeechStart(var1)
            }

            override fun onSpeechProgressChanged(var1: String?, var2: Int) {
                listener?.onSpeechProgressChanged(var1, var2)
            }

            override fun onSpeechFinish(var1: String?) {
                listener?.onSpeechFinish(var1)
            }

            override fun onError(var1: String?) {
                listener?.onError(var1)
            }
        })
    }
}