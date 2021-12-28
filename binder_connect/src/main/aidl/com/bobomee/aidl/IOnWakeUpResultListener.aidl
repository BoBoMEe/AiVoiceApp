package com.bobomee.aidl;

interface IOnWakeUpResultListener {
    //唤醒准备就绪
    void wakeUpReady();

    //唤醒成功
    void wakeUpSuccess(String result);

    //唤醒错误
    void wakeUpError(String text);
}