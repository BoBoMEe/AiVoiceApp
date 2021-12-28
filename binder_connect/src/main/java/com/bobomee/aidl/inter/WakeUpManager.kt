package com.bobomee.aidl.inter

import com.bobomee.aidl.callback.OnWakeUpResultListener

interface WakeUpManager {
    //启动唤醒
    fun startWakeUp()

    //停止唤醒
    fun stopWakeUp()

    fun registerWakeUpListener(listener: OnWakeUpResultListener?)
}