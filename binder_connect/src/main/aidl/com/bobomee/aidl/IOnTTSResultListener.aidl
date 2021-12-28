package com.bobomee.aidl;

interface IOnTTSResultListener {

    void onSpeechStart(String var1);

    void onSpeechProgressChanged(String var1, int var2);

    void onSpeechFinish(String var1);

    void onError(String var1);
}
