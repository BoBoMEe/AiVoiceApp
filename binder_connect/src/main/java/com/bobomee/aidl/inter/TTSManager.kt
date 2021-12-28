package com.bobomee.aidl.inter

import com.bobomee.aidl.callback.OnTTSResultListener

interface TTSManager {
    //设置发音人
    fun setPeople(people: String?)

    //设置语速
    fun setVoiceSpeed(speed: String?)

    //设置音量
    fun setVoiceVolume(volume: String?)

    //播放
    fun ttsStart(text: String?)

    //暂停
    fun ttsPause()

    //继续播放
    fun ttsResume()

    //停止播放
    fun ttsStop()

    //释放
    fun ttsRelease()

    fun registerTTSListener(listener: OnTTSResultListener?)
}