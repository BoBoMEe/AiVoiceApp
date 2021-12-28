package com.bobomee.lib_network.impl

import com.bobomee.lib_network.HttpManager
import com.bobomee.lib_network.bean.*
import com.bobomee.lib_network.http.HttpUrl
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


/**
 * Profile: 接口服务
 */
interface HttpImplService {

    //==============================笑话=============================

    @GET(HttpUrl.JOKE_ONE_ACTION)
    fun queryJoke(@Query("key") key: String): Call<JokeOneData>

    @GET(HttpUrl.JOKE_LIST_ACTION)
    fun queryJokeList(
        @Query("key") key: String,
        @Query("page") page: Int,
        @Query("pagesize") pageSize: Int
    ): Call<JokeListData>

    //==============================机器人=============================
    @Headers(HttpManager.JSON)
    @POST(HttpUrl.ROBOT_ACTION)
    fun aiRobot(@Body requestBody: RequestBody): Call<RobotData>

    //==============================天气=============================
    @GET(HttpUrl.WEATHER_ACTION)
    fun getWeather(@Query("city") city: String, @Query("key") key: String): Call<WeatherData>


}