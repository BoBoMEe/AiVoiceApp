package com.bobomee.binder_server.asr

import android.content.Context
import com.baidu.speech.EventListener
import com.baidu.speech.EventManager
import com.baidu.speech.EventManagerFactory
import com.baidu.speech.asr.SpeechConstant
import com.bobomee.aidl.callback.OnAsrResultListener
import com.bobomee.aidl.callback.OnNluResultListener
import org.json.JSONObject


/**
 * Profile: 语音识别
 */
object VoiceAsr : EventListener {

    //识别对象
    private lateinit var asr: EventManager

    private lateinit var asrJson: String

    private var mOnOnAsrResultListener: OnAsrResultListener?=null
    private var mOnOnNluResultListener: OnNluResultListener?=null

    fun initAsr(mContext: Context) {
        val map = HashMap<Any, Any>()
        map[SpeechConstant.ACCEPT_AUDIO_VOLUME] = true
        map[SpeechConstant.DISABLE_PUNCTUATION] = false
        map[SpeechConstant.PID] = 15363 //15373
        asrJson = JSONObject(map as Map<Any, Any>).toString()

        asr = EventManagerFactory.create(mContext, "asr")
    }

    fun register(onOnAsrResultListener: OnAsrResultListener){
        this.mOnOnAsrResultListener = onOnAsrResultListener
        asr.registerListener(this)
    }

    fun register(onOnNluResultListener: OnNluResultListener){
        this.mOnOnNluResultListener = onOnNluResultListener
    }

    //开始识别
    fun startAsr() {
        asr.send(SpeechConstant.ASR_START, asrJson, null, 0, 0)
    }

    //停止识别
    fun stopAsr() {
        asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0)
    }

    //取消识别
    fun cancelAsr() {
        asr.send(SpeechConstant.ASR_CANCEL, null, null, 0, 0)
    }

    //销毁
    fun releaseAsr() {
        asr.unregisterListener(this)
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
            SpeechConstant.CALLBACK_EVENT_ASR_BEGIN -> mOnOnAsrResultListener?.asrStartSpeak()
            SpeechConstant.CALLBACK_EVENT_ASR_END -> mOnOnAsrResultListener?.asrStopSpeak()
        }
        //去除脏数据
        if (params == null) {
            return
        }
        val allJson = JSONObject(params)
        when (name) {
            SpeechConstant.CALLBACK_EVENT_ASR_FINISH -> mOnOnAsrResultListener?.asrResult(allJson.toString())
            SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL -> {
                mOnOnAsrResultListener?.updateUserText(allJson.optString("best_result"))
                byte?.let {
                    mOnOnNluResultListener?.nluResult(String(byte, offset, length))
                }
            }
            SpeechConstant.CALLBACK_EVENT_ASR_VOLUME -> {
                val js = JSONObject(params)
                mOnOnAsrResultListener?.asrVolume(js.optInt("volume"))
            }

        }
    }
}












