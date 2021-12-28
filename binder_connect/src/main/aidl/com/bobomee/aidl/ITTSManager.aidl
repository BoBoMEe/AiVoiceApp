package com.bobomee.aidl;
import com.bobomee.aidl.IOnTTSResultListener;

interface ITTSManager {

    //设置发音人
    void setPeople(String  people);

    //设置语速
    void setVoiceSpeed(String speed);

    //设置音量
    void setVoiceVolume(String volume);

    //播放
    void ttsStart(String text);

    //暂停
    void ttsPause();

    //继续播放
    void ttsResume();

    //停止播放
    void ttsStop();

    //释放
    void ttsRelease();

    void registerTTSListener(IOnTTSResultListener listener);
}
