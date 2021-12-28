package com.bobomee.aidl;

interface IOnAsrResultListener {

    //开始说话
    void asrStartSpeak();

    //停止说话
    void asrStopSpeak();

    //更新话术
    void updateUserText(String text);

    //在线识别结果
    void asrResult(String result);

    //音量
    void asrVolume(int volume);

}