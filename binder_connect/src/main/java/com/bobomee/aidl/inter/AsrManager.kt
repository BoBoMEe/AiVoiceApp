package com.bobomee.aidl.inter

import com.bobomee.aidl.callback.OnAsrResultListener
import com.bobomee.aidl.callback.OnNluResultListener

interface AsrManager {
    //开始识别
    fun startAsr()

    //停止识别
    fun stopAsr()

    //取消识别
    fun cancelAsr()

    //销毁
    fun releaseAsr()

    fun registerAsrListener(listenerOn: OnAsrResultListener?)

    fun registerNluListener(listenerOn: OnNluResultListener?)
}