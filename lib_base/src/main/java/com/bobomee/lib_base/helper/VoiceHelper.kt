package com.bobomee.lib_base.helper

import com.bobomee.aidl.IAsrManager
import com.bobomee.aidl.ITTSManager
import com.bobomee.aidl.IWakeUpManager
import com.bobomee.aidl.callback.OnAsrResultListener
import com.bobomee.aidl.callback.OnNluResultListener
import com.bobomee.aidl.callback.OnTTSResultListener
import com.bobomee.aidl.callback.OnWakeUpResultListener
import com.bobomee.aidl.inter.AsrManager
import com.bobomee.aidl.inter.TTSManager
import com.bobomee.aidl.inter.WakeUpManager
import com.bobomee.binder_connect.ServiceTag
import com.bobomee.binder_connect.ServiceTools

object VoiceHelper : AsrManager, WakeUpManager, TTSManager {
    private val mWakeUpManager by lazy {
        ServiceTools.findService<IWakeUpManager>(
            ServiceTag.WAKE_UP
        )
    }
    private val mTTsManager by lazy {
        ServiceTools.findService<ITTSManager>(
            ServiceTag.TTS
        )
    }
    private val mAsrManager by lazy{
        ServiceTools.findService<IAsrManager>(
            ServiceTag.ASR
        )
    }

    override fun startAsr() {
        mAsrManager?.startAsr()
    }

    override fun stopAsr() {
        mAsrManager?.stopAsr()
    }

    override fun cancelAsr() {
        mAsrManager?.cancelAsr()
    }

    override fun releaseAsr() {
        mAsrManager?.releaseAsr()
    }

    override fun registerAsrListener(listenerOn: OnAsrResultListener?) {
        mAsrManager?.registerAsrListener(listenerOn)
    }

    override fun registerNluListener(listenerOn: OnNluResultListener?) {
        mAsrManager?.registerNluListener(listenerOn)
    }

    override fun startWakeUp() {
        mWakeUpManager?.startWakeUp()
    }

    override fun stopWakeUp() {
        mWakeUpManager?.stopWakeUp()
    }

    override fun registerWakeUpListener(listener: OnWakeUpResultListener?) {
        mWakeUpManager?.registerWakeUpListener(listener)
    }

    override fun setPeople(people: String?) {
        mTTsManager?.setPeople(people)
    }

    override fun setVoiceSpeed(speed: String?) {
        mTTsManager?.setVoiceSpeed(speed)
    }

    override fun setVoiceVolume(volume: String?) {
        mTTsManager?.setVoiceVolume(volume)
    }

    override fun ttsStart(text: String?) {
        mTTsManager?.ttsStart(text)
    }

    override fun ttsPause() {
        mTTsManager?.ttsPause()
    }

    override fun ttsResume() {
        mTTsManager?.ttsRelease()
    }

    override fun ttsStop() {
        mTTsManager?.ttsStop()
    }

    override fun ttsRelease() {
        mTTsManager?.ttsRelease()
    }

    override fun registerTTSListener(listener: OnTTSResultListener?) {
        mTTsManager?.registerTTSListener(listener)
    }

}