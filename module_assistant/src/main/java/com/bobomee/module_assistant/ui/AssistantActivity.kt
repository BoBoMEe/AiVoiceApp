package com.bobomee.module_assistant.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.alibaba.android.arouter.facade.annotation.Route
import com.bobomee.module_assistant.data.MainListData
import com.bobomee.module_assistant.helper.ContactHelper
import com.bobomee.lib_base.base.view.BaseActivity
import com.bobomee.lib_base.base.adapter.BasePagerAdapter
import com.bobomee.lib_base.event.EventManager
import com.bobomee.lib_base.event.MessageEvent
import com.bobomee.lib_base.helper.ARouterHelper
import com.bobomee.module_assistant.BuildConfig
import com.bobomee.module_assistant.R
import com.bobomee.module_assistant.service.VoiceService
import com.yanzhenjie.permission.Action
import com.zhy.magicviewpager.transformer.ScaleInTransformer
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

@Route(path = ARouterHelper.PATH_ASSISTANT)
class AssistantActivity : BaseActivity() {

    //不建议一股脑的申请权限 而是应该根据使用到的场景是让用户同意权限
    private val permission = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CALL_PHONE,
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.VIBRATE,
        Manifest.permission.CAMERA
    )

    private val mList = ArrayList<MainListData>()
    private val mListView = ArrayList<View>()

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getTitleText(): String {
        return getString(R.string.app_name)
    }

    override fun isShowBack(): Boolean {
        return false
    }

    override fun initView() {

        EventManager.register(this)

        //动态权限
        if (checkPermission(permission)) {
            linkService()
        } else {
            requestPermission(permission,
                Action<List<String>> { linkService() })
        }

        //窗口权限
        if (!checkWindowPermission()) {
            requestWindowPermission(packageName)
        }

        //点击唤醒
        ivMainVoice.setOnClickListener { EventManager.post(EventManager.WAKE_UP) }

        initPagerData()

        initPagerView()
    }

    //初始化View
    private fun initPagerView() {
        //mViewPager.pageMargin = 20
        mViewPager.offscreenPageLimit = mList.size
        mViewPager.adapter = BasePagerAdapter(mListView)
        mViewPager.setPageTransformer(true, ScaleInTransformer())
    }

    //初始化数据
    private fun initPagerData() {
        val title = resources.getStringArray(R.array.MainTitleArray)
        val desc = resources.getStringArray(R.array.MainDescArray)
        val icon = resources.obtainTypedArray(R.array.MainIconArray)

        for ((index, value) in title.withIndex()) {
            mList.add(MainListData(value, desc[index], icon.getResourceId(index, 0)))
        }
        icon.recycle()

        //非调试版本去除工程模式
        if (!BuildConfig.DEBUG) {
            mList.removeAt(mList.size - 1)
        }

        mList.forEach {
            val view = View.inflate(this, R.layout.layout_main_list, null)
            val mCvMainView = view.findViewById<CardView>(R.id.mCvMainView)
            val mTvTitle = view.findViewById<TextView>(R.id.mTvTitle)
            val mTvContext = view.findViewById<TextView>(R.id.mTvContext)

            mCvMainView.setBackgroundResource(it.icon)

            mTvTitle.text = it.title
            mTvContext.text = it.desc

            //mCvMainView.layoutParams?.let { lp ->
            //lp.height = windowHeight / 5 * 3
            //}

            //点击事件
            view.setOnClickListener { _ ->
                when (it.icon) {
                    R.drawable.img_main_weather -> ARouterHelper.startActivity(ARouterHelper.PATH_WEATHER)
                    R.drawable.img_main_joke_icon -> ARouterHelper.startActivity(ARouterHelper.PATH_JOKE)
                    R.drawable.img_main_map_icon -> ARouterHelper.startActivity(ARouterHelper.PATH_MAP)
                    R.drawable.img_main_voice_setting -> ARouterHelper.startActivity(ARouterHelper.PATH_VOICE_SETTING)
                    R.drawable.img_main_system_setting -> ARouterHelper.startActivity(ARouterHelper.PATH_SETTING)
                    R.drawable.img_main_developer -> ARouterHelper.startActivity(ARouterHelper.PATH_DEVELOPER)
                }
            }

            mListView.add(view)
        }
    }

    //连接服务
    private fun linkService() {

        //读取联系人
        ContactHelper.initHelper(this)

        startService(Intent(this, VoiceService::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        EventManager.unRegister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessage(message: MessageEvent) {
        when (message.type) {
            EventManager.VOLUME_ASR -> mVoiceLine.setVolume(message.intValue)
        }
    }

    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, AssistantActivity::class.java)
            context.startActivity(starter)
        }
    }
}
