package com.bobomee.aidl;
import com.bobomee.aidl.IOnAsrResultListener;
import com.bobomee.aidl.IOnNluResultListener;

interface IAsrManager {

    //开始识别
    void startAsr();

    //停止识别
    void stopAsr();

    //取消识别
    void cancelAsr();

    //销毁
    void releaseAsr();

    void registerAsrListener(IOnAsrResultListener listener);

    void registerNluListener(IOnNluResultListener listener);
}
