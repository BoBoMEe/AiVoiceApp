package com.bobomee.aidl;
import com.bobomee.aidl.IOnWakeUpResultListener;

interface IWakeUpManager {
    //启动唤醒
    void startWakeUp();

    //停止唤醒
    void stopWakeUp();

    void registerWakeUpListener(IOnWakeUpResultListener listener);
}