package com.bobomee.lib_network

import com.bobomee.lib_network.bean.*
import com.bobomee.lib_network.http.HttpKey
import com.bobomee.lib_network.http.HttpUrl
import com.bobomee.lib_network.impl.HttpImplService
import okhttp3.*
import org.json.JSONObject
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Profile: 对外的网络管理类
 */
object HttpManager {

    private const val PAGE_SIZE = 20

    const val JSON = "Content-type:application/json;charset=UTF-8"

    //创建客户端
    private fun getClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    //============================笑话============================

    //笑话对象
    private val retrofitJoke by lazy {
        Retrofit.Builder()
            .client(getClient())
            .baseUrl(HttpUrl.JOKE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //笑话接口对象
    private val apiJoke by lazy {
        retrofitJoke.create(HttpImplService::class.java)
    }

    //查询笑话
    fun queryJoke(callback: Callback<JokeOneData>) {
        apiJoke.queryJoke(HttpKey.JOKE_KEY).enqueue(callback)
    }

    //查询笑话列表
    fun queryJokeList(page: Int, callback: Callback<JokeListData>) {
        apiJoke.queryJokeList(HttpKey.JOKE_KEY, page, PAGE_SIZE).enqueue(callback)
    }

    //============================笑话============================

    //============================机器人============================

    //星座对象
    private val retrofitAiRobot by lazy {
        Retrofit.Builder()
            .client(getClient())
            .baseUrl(HttpUrl.ROBOT_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //星座接口对象
    private val apiRobot by lazy {
        retrofitAiRobot.create(HttpImplService::class.java)
    }

    //机器人对话
    fun aiRobotChat(text: String, callback: Callback<RobotData>) {
        val jsonObject = JSONObject()
        jsonObject.put("reqType", 0)

        val perception = JSONObject()

        val inputText = JSONObject()
        inputText.put("text", text)

        perception.put("inputText", inputText)

        val userInfo = JSONObject()
        userInfo.put("apiKey", HttpKey.ROBOT_KEY)
        userInfo.put("userId", "ai")

        jsonObject.put("perception", perception)
        jsonObject.put("userInfo", userInfo)

        val body = RequestBody.create(MediaType.parse(JSON), jsonObject.toString())

        apiRobot.aiRobot(body).enqueue(callback)
    }

    //============================机器人============================

    //============================天气============================

    //天气对象
    private val retrofitWeather by lazy {
        Retrofit.Builder()
            .client(getClient())
            .baseUrl(HttpUrl.WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //天气接口对象
    private val apiWeather by lazy {
        retrofitWeather.create(HttpImplService::class.java)
    }

    //查询天气
    fun queryWeather(city: String, callback: Callback<WeatherData>) {
        apiWeather.getWeather(city, HttpKey.WEATHER_KEY).enqueue(callback)
    }

    //============================天气============================


}