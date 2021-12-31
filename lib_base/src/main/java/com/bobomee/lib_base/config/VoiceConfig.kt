package com.bobomee.lib_base.config

import com.bobomee.lib_base.utils.SpUtils

object VoiceConfig {

    // 是否播报欢迎词
    const val isHello :String = "isHello"

    // 是否后台播放笑话
    const val playJokeBackground = "isJoke"

    // tts 播报语速
    const val ttsPlaySpeed = "tts_speed"

    // tts 播报音量
    const val ttsVolume = "tts_volume"

    // tts 播报音色
    const val ttsPeople = "tts_people"

    // 天气：当前选择城市
    const val weatherCity = "city"
}

fun SpUtils.isHello():Boolean{
    return getBoolean(VoiceConfig.isHello, true)
}

fun SpUtils.setHello(hello:Boolean){
    putBoolean(VoiceConfig.isHello,hello)
}

fun SpUtils.isBackJoke():Boolean{
    return getBoolean(VoiceConfig.playJokeBackground,true)
}

fun SpUtils.setBackJoke(backJoke:Boolean){
    putBoolean(VoiceConfig.playJokeBackground,backJoke)
}

fun SpUtils.ttsPlaySpeed():Int{
    return getInt(VoiceConfig.ttsPlaySpeed,5)
}

fun SpUtils.setTtsPlaySpeed(speed:Int){
    putInt(VoiceConfig.ttsPlaySpeed,speed)
}

fun SpUtils.ttsVolume():Int{
    return getInt(VoiceConfig.ttsVolume,5)
}

fun SpUtils.setTtsVolume(volume:Int){
    putInt(VoiceConfig.ttsVolume,volume)
}

fun SpUtils.ttsPeople():String{
    return getString(VoiceConfig.ttsPeople,"0")
}

fun SpUtils.setTtsPeople(people:String){
    putString(VoiceConfig.ttsPeople,people)
}

fun SpUtils.weatherCity():String{
    return getString(VoiceConfig.weatherCity,"北京")
}

fun SpUtils.setWeatherCity(city:String){
    putString(VoiceConfig.weatherCity,city)
}
