package com.bobomee.module_assistant.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bobomee.aidl.callback.OnAsrResultListener
import com.bobomee.aidl.callback.OnNluResultListener
import com.bobomee.aidl.callback.OnTTSResultListener
import com.bobomee.aidl.callback.OnWakeUpResultListener
import com.bobomee.lib_base.config.isHello
import com.bobomee.lib_base.config.ttsPeople
import com.bobomee.lib_base.config.ttsPlaySpeed
import com.bobomee.lib_base.config.ttsVolume
import com.bobomee.module_assistant.adapter.ChatListAdapter
import com.bobomee.module_assistant.data.ChatList
import com.bobomee.module_assistant.engine.VoiceEngineAnalyze
import com.bobomee.module_assistant.entity.AppConstants
import com.bobomee.module_assistant.error.VoiceErrorCode
import com.bobomee.module_assistant.helper.ContactHelper
import com.bobomee.module_assistant.listener.OnNluAnalyzeListener
import com.bobomee.module_assistant.words.WordsTools
import com.bobomee.lib_base.event.EventManager
import com.bobomee.lib_base.event.MessageEvent
import com.bobomee.lib_base.helper.ARouterHelper
import com.bobomee.lib_base.helper.CommonSettingHelper
import com.bobomee.lib_base.helper.NotificationHelper
import com.bobomee.lib_base.helper.VoiceHelper
import com.bobomee.lib_base.utils.LogUtils
import com.bobomee.lib_base.utils.SoundPoolUtils
import com.bobomee.lib_base.utils.SpUtils
import com.bobomee.lib_base.utils.WindowUtils
import com.bobomee.lib_network.HttpManager
import com.bobomee.lib_network.bean.JokeOneData
import com.bobomee.lib_network.bean.RobotData
import com.bobomee.lib_network.bean.WeatherData
import com.bobomee.module_assistant.R
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VoiceService : Service(), OnNluAnalyzeListener {

    private val mHandler = Handler()

    private lateinit var mFullWindowView: View
    private lateinit var mChatListView: RecyclerView
    private lateinit var mLottieView: LottieAnimationView
    private lateinit var tvVoiceTips: TextView
    private lateinit var ivCloseWindow: ImageView
    private lateinit var mChatAdapter: ChatListAdapter
    private val mList = ArrayList<ChatList>()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        LogUtils.i("??????????????????")
        initCoreVoiceService()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        bindNotification()
        return START_STICKY_COMPATIBILITY
    }

    //???????????????
    private fun bindNotification() {
        startForeground(
            1000,
            NotificationHelper.bindVoiceService()
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        EventManager.unRegister(this)
    }

    //?????????????????????
    private fun initCoreVoiceService() {

        EventManager.register(this)

        WindowUtils.initUtils(this)
        mFullWindowView = WindowUtils.getView(R.layout.layout_window_item)
        mChatListView = mFullWindowView.findViewById<RecyclerView>(R.id.mChatListView)
        mLottieView = mFullWindowView.findViewById<LottieAnimationView>(R.id.mLottieView)
        ivCloseWindow = mFullWindowView.findViewById<ImageView>(R.id.ivCloseWindow)
        tvVoiceTips = mFullWindowView.findViewById<TextView>(R.id.tvVoiceTips)
        mChatListView.layoutManager = LinearLayoutManager(this)
        mChatAdapter = ChatListAdapter(mList)
        mChatListView.adapter = mChatAdapter

        ivCloseWindow.setOnClickListener {
            VoiceHelper.ttsStop()
            hideTouchWindow()
        }

        VoiceHelper.registerWakeUpListener(object : OnWakeUpResultListener() {
            override fun wakeUpReady() {
                LogUtils.i("??????????????????")

                //  ???????????????????????????????????????
                VoiceHelper.setPeople(SpUtils.ttsPeople())
                VoiceHelper.setVoiceSpeed(SpUtils.ttsPlaySpeed().toString())
                VoiceHelper.setVoiceVolume(SpUtils.ttsVolume().toString())

                val isHello = SpUtils.isHello()
                if (isHello) {
                    addAiText("????????????????????????")
                }
            }

            override fun wakeUpSuccess(result: String?) {
                LogUtils.i("???????????????$result")
                //??????????????????????????????????????????????????????
                val resultJson = JSONObject(result ?: "")
                val errorCode = resultJson.optInt("errorCode")
                //????????????
                if (errorCode == 0) {
                    //?????????
                    val word = resultJson.optString("word")
                    if (word == getString(R.string.text_voice_wakeup_text)) {
                        wakeUpFix()
                    }
                }
            }

            override fun wakeUpError(text: String?) {
                LogUtils.e("???????????????$text")
                hideWindow()
            }
        })

        VoiceHelper.registerAsrListener(object : OnAsrResultListener() {

            override fun asrStartSpeak() {
                LogUtils.i("????????????")
            }

            override fun asrStopSpeak() {
                LogUtils.i("????????????")
            }

            override fun updateUserText(text: String?) {
                updateTips(text ?: "")
            }

            override fun asrResult(result: String?) {
                LogUtils.i("====================RESULT======================")
                LogUtils.i("result???$result")
                val resultJson = JSONObject(result ?: "")
                val errNo = resultJson.optInt("err_no")
                if (errNo != 0) {
                    val errorCode = resultJson.optInt("sub_error")
                    addAiText(VoiceErrorCode.fixErrorCode(errorCode))
                    hideWindow()
                }
            }

            override fun asrVolume(volume: Int) {
                EventManager.post(EventManager.VOLUME_ASR, volume)
            }
        })

        VoiceHelper.registerNluListener(object : OnNluResultListener() {
            override fun nluResult(nlu: String?) {
                LogUtils.i("====================NLU======================")
                LogUtils.i("nlu???$nlu")
                val nluJson = JSONObject(nlu ?: "")
                addMineText(nluJson.optString("raw_text"))
                VoiceEngineAnalyze.analyzeNlu(nluJson, this@VoiceService)
            }
        })

        VoiceHelper.startWakeUp()
    }

    /**
     * ???????????????????????????
     */
    private fun wakeUpFix() {
        showWindow()
        updateTips(getString(R.string.text_voice_wakeup_tips))
        SoundPoolUtils.play(R.raw.record_start)
        //??????
        val wakeupText = WordsTools.wakeupWords()
        addAiText(wakeupText,
            object : OnTTSResultListener() {
                override fun onSpeechFinish(var1: String?) {
                    super.onSpeechFinish(var1)
                    //????????????
                    VoiceHelper.startAsr()
                }
            })
    }

    //????????????
    private fun showWindow() {
        LogUtils.i("======????????????======")
        mLottieView.playAnimation()
        WindowUtils.show(mFullWindowView)
    }

    //????????????
    private fun hideWindow() {
        LogUtils.i("======????????????======")
        mHandler.postDelayed({
            WindowUtils.hide(mFullWindowView)
            mLottieView.pauseAnimation()
            SoundPoolUtils.play(R.raw.record_over)
        }, 2 * 1000)
    }

    //??????????????????
    private fun hideTouchWindow() {
        LogUtils.i("======????????????======")
        WindowUtils.hide(mFullWindowView)
        mLottieView.pauseAnimation()
        SoundPoolUtils.play(R.raw.record_over)
        VoiceHelper.stopAsr()
    }

    //??????
    override fun back() {
        addAiText(getString(R.string.text_voice_back_text))
        CommonSettingHelper.back()
        hideWindow()
    }

    //??????
    override fun home() {
        addAiText(getString(R.string.text_voice_home_text))
        CommonSettingHelper.home()
        hideWindow()
    }

    //??????+
    override fun setVolumeUp() {
        addAiText(getString(R.string.text_voice_volume_add))
        CommonSettingHelper.setVolumeUp()
        hideWindow()
    }

    //??????-
    override fun setVolumeDown() {
        addAiText(getString(R.string.text_voice_volume_sub))
        CommonSettingHelper.setVolumeDown()
        hideWindow()
    }

    //??????
    override fun quit() {
        addAiText(WordsTools.quitWords(), object : OnTTSResultListener() {
            override fun onSpeechFinish(var1: String?) {
                super.onSpeechFinish(var1)
                hideTouchWindow()
            }
        })
    }

    //???????????????
    override fun callPhoneForName(name: String) {
        val list = ContactHelper.mContactList.filter { it.phoneName == name }
        if (list.isNotEmpty()) {
            addAiText(
                getString(R.string.text_voice_call, name),
                object : OnTTSResultListener() {
                    override fun onSpeechFinish(var1: String?) {
                        super.onSpeechFinish(var1)
                        ContactHelper.callPhone(list[0].phoneNumber)
                    }
                })
        } else {
            addAiText(getString(R.string.text_voice_no_friend))
        }
        hideWindow()
    }

    //????????????
    override fun callPhoneForNumber(phone: String) {
        addAiText(getString(R.string.text_voice_call, phone), object : OnTTSResultListener() {
            override fun onSpeechFinish(var1: String?) {
                super.onSpeechFinish(var1)
                ContactHelper.callPhone(phone)
            }
        })
        hideWindow()
    }

    //????????????
    override fun playJoke() {
        HttpManager.queryJoke(object : Callback<JokeOneData> {
            override fun onFailure(call: Call<JokeOneData>, t: Throwable) {
                LogUtils.i("onFailure:$t")
                jokeError()
            }

            override fun onResponse(call: Call<JokeOneData>, response: Response<JokeOneData>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.error_code == 0) {
                            //??????Result????????????????????????????????????
                            val index = WordsTools.randomInt(it.result.size)
                            if (index < it.result.size) {
                                val data = it.result[index]
                                val content = data.content
                                LogUtils.i("Joke onResponse : $content")
                                addAiText(content, object : OnTTSResultListener() {
                                    override fun onSpeechFinish(var1: String?) {
                                        super.onSpeechFinish(var1)
                                        hideWindow()
                                    }
                                })
                            }
                        } else {
                            jokeError()
                        }
                    }
                } else {
                    jokeError()
                }
            }
        })
    }

    //????????????
    override fun jokeList() {
        addAiText(getString(R.string.text_voice_query_joke))
        ARouterHelper.startActivity(ARouterHelper.PATH_JOKE)
        hideWindow()
    }

    //?????????
    override fun aiRobot(text: String) {
        //?????????????????????
        HttpManager.aiRobotChat(text, object : Callback<RobotData> {

            override fun onFailure(call: Call<RobotData>, t: Throwable) {
                addAiText(WordsTools.noAnswerWords())
                hideWindow()
            }

            override fun onResponse(
                call: Call<RobotData>,
                response: Response<RobotData>
            ) {
                LogUtils.i("???????????????:" + response.body().toString())
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.intent.code == 10004) {
                            //??????
                            if (it.results.isEmpty()) {
                                addAiText(WordsTools.noAnswerWords())
                                hideWindow()
                            } else {
                                addAiText(it.results[0].values.text)
                                hideWindow()
                            }
                        } else {
                            addAiText(WordsTools.noAnswerWords())
                            hideWindow()
                        }
                    }
                }
            }

        })
    }

    //????????????
    override fun queryWeather(city: String) {
        HttpManager.run {
            queryWeather(city, object : Callback<WeatherData> {
                override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                    addAiText(getString(R.string.text_voice_query_weather_error, city))
                    hideWindow()
                }

                override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            it.result.realtime.apply {
                                //???UI?????????
                                addWeather(
                                    city,
                                    wid,
                                    info,
                                    temperature,
                                    aqi,
                                    object : com.bobomee.aidl.callback.OnTTSResultListener() {
                                        override fun onSpeechFinish(var1: String?) {
                                            super.onSpeechFinish(var1)
                                            hideWindow()
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            })
        }
    }

    //????????????
    override fun queryWeatherInfo(city: String) {
        addAiText(getString(R.string.text_voice_query_weather, city))
        ARouterHelper.startActivity(ARouterHelper.PATH_WEATHER, "city", city)
        hideWindow()
    }

    //????????????
    override fun nearByMap(poi: String) {
        LogUtils.i("nearByMap:$poi")
        addAiText(getString(R.string.text_voice_query_poi, poi))
        ARouterHelper.startActivity(ARouterHelper.PATH_MAP, "type", "poi", "keyword", poi)
        hideWindow()
    }

    //???????????? + ??????
    override fun routeMap(address: String) {
        LogUtils.i("routeMap:$address")
        addAiText(getString(R.string.text_voice_query_navi, address))
        ARouterHelper.startActivity(ARouterHelper.PATH_MAP, "type", "route", "keyword", address)
        hideWindow()
    }

    //????????????
    override fun nluError() {
        //????????????
        addAiText(WordsTools.noSupportWords())
        hideWindow()
    }

    /**
     * ??????????????????
     */
    private fun addMineText(text: String) {
        val bean = ChatList(AppConstants.TYPE_MINE_TEXT)
        bean.text = text
        baseAddItem(bean)
    }

    /**
     * ??????AI??????
     */
    private fun addAiText(text: String) {
        val bean = ChatList(AppConstants.TYPE_AI_TEXT)
        bean.text = text
        baseAddItem(bean)
        VoiceHelper.ttsStart(text)
    }

    /**
     * ??????AI??????
     */
    private fun addAiText(text: String, mOnTTSEndListener: OnTTSResultListener) {
        val bean = ChatList(AppConstants.TYPE_AI_TEXT)
        bean.text = text
        baseAddItem(bean)
        VoiceHelper.registerTTSListener(mOnTTSEndListener)
        VoiceHelper.ttsStart(text)
    }

    /**
     * ????????????
     */
    private fun addWeather(
        city: String,
        wid: String,
        info: String,
        temperature: String,
        air: String,
        mOnTTSEndListener: OnTTSResultListener
    ) {
        val bean = ChatList(AppConstants.TYPE_AI_WEATHER)
        bean.city = city
        bean.wid = wid
        bean.info = info
        bean.temperature = temperature
        bean.air = air

        baseAddItem(bean)
        val text = city + "????????????" + info + temperature + "??"
        VoiceHelper.registerTTSListener(mOnTTSEndListener)
        VoiceHelper.ttsStart(text)
    }


    /**
     * ????????????
     */
    private fun baseAddItem(bean: ChatList) {
        mList.add(bean)
        mChatAdapter.notifyItemInserted(mList.size - 1)
        //???????????????
        mChatListView.scrollToPosition(mList.size - 1)
    }

    /**
     * ???????????????
     */
    private fun updateTips(text: String) {
        tvVoiceTips.text = text
    }

    /**
     * ????????????
     */
    private fun jokeError() {
        hideWindow()
        addAiText(getString(R.string.text_voice_query_joke_error))
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessage(message: MessageEvent) {
        when (message.type) {
            EventManager.WAKE_UP -> wakeUpFix()
        }
    }
}