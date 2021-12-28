package com.bobomee.binder_server.wakeup

import android.content.Context
import com.baidu.speech.EventListener
import com.baidu.speech.EventManager
import com.baidu.speech.EventManagerFactory
import com.baidu.speech.asr.SpeechConstant
import com.bobomee.aidl.callback.OnWakeUpResultListener
import com.bobomee.binder_server.entity.VoiceConstants
import org.json.JSONObject


/**
 * Profile: 语音唤醒
 */
object VoiceWakeUp :EventListener{

    //启动数据
    private lateinit var wakeUpJson: String

    //唤醒对象
    private lateinit var wp: EventManager

    private var mOnOnWakeUpResultListener: OnWakeUpResultListener?= null

    //初始化唤醒
    fun initWakeUp(mContext: Context) {
        val map = HashMap<Any, Any>()
        //本地文本路径
        map[SpeechConstant.WP_WORDS_FILE] = "assets:///WakeUp.bin"
        map[SpeechConstant.APP_ID] = VoiceConstants.VOICE_APP_ID
        //是否获取音量
        map[SpeechConstant.ACCEPT_AUDIO_VOLUME] = true
        map[SpeechConstant.ACCEPT_AUDIO_DATA] = true
        //转换成Json
        wakeUpJson = JSONObject(map as Map<Any, Any>).toString()

        //设置监听器
        wp = EventManagerFactory.create(mContext, "wp")
    }

    fun register(onOnWakeUpResultListener: OnWakeUpResultListener){
        this.mOnOnWakeUpResultListener  = onOnWakeUpResultListener
        wp.registerListener(this)
    }

    //启动唤醒
    fun startWakeUp() {
        wp.send(SpeechConstant.WAKEUP_START, wakeUpJson, null, 0, 0)
    }

    //停止唤醒
    fun stopWakeUp() {
        wp.send(SpeechConstant.WAKEUP_STOP, null, null, 0, 0)
    }

    override fun onEvent(
        name: String?,
        params: String?,
        byte: ByteArray?,
        offset: Int,
        length: Int
    ) {
        //语音前置状态
        when (name) {
            SpeechConstant.CALLBACK_EVENT_WAKEUP_READY -> mOnOnWakeUpResultListener?.wakeUpReady()
        }
        //去除脏数据
        if (params == null) {
            return
        }
        val allJson = JSONObject(params)
        when (name) {
            SpeechConstant.CALLBACK_EVENT_WAKEUP_ERROR -> mOnOnWakeUpResultListener?.wakeUpError("唤醒失败")
            SpeechConstant.CALLBACK_EVENT_WAKEUP_SUCCESS -> mOnOnWakeUpResultListener?.wakeUpSuccess(
                allJson.toString()
            )
        }
    }
}