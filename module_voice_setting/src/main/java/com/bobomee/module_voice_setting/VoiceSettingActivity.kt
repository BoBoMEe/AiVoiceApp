package com.bobomee.module_voice_setting

import android.widget.SeekBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.bobomee.lib_base.base.view.BaseActivity
import com.bobomee.lib_base.base.adapter.CommonAdapter
import com.bobomee.lib_base.base.adapter.CommonViewHolder
import com.bobomee.lib_base.config.*
import com.bobomee.lib_base.helper.ARouterHelper
import com.bobomee.lib_base.helper.VoiceHelper
import com.bobomee.lib_base.utils.SpUtils
import kotlinx.android.synthetic.main.activity_voice_setting.*

/**
 * Profile: 语音管理
 */
@Route(path = ARouterHelper.PATH_VOICE_SETTING)
class VoiceSettingActivity : BaseActivity(){

    private val mList: ArrayList<String> = ArrayList()
    private var mTtsPeopleIndex: Array<String>? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_voice_setting
    }

    override fun getTitleText(): String {
        return title.toString()
    }

    override fun isShowBack(): Boolean {
        return true
    }

    override fun initView() {
        //默认值
        bar_voice_speed.progress = SpUtils.ttsPlaySpeed()
        bar_voice_volume.progress = SpUtils.ttsVolume()

        //设置最大值
        bar_voice_speed.max = 15
        bar_voice_volume.max = 15

        initData()
        initListener()
        initPeopleView()
    }

    //初始化数据
    private fun initData() {
        val mTtsPeople = resources.getStringArray(R.array.TTSPeople)

        mTtsPeopleIndex = resources.getStringArray(R.array.TTSPeopleIndex)
        mTtsPeople.forEach { mList.add(it) }
    }

    //初始化发音人列表
    private fun initPeopleView() {
        rv_voice_people.layoutManager = LinearLayoutManager(this)
        rv_voice_people.isNestedScrollingEnabled = true
        rv_voice_people.adapter =
            CommonAdapter(mList, object : CommonAdapter.OnBindDataListener<String> {
                override fun onBindViewHolder(
                    model: String,
                    viewHolder: CommonViewHolder,
                    type: Int,
                    position: Int
                ) {
                    viewHolder.setText(R.id.mTvPeopleContent, model)
                    viewHolder.itemView.setOnClickListener {
                        mTtsPeopleIndex?.let {
                            val ttsPeople = it[position]
                            VoiceHelper.setPeople(ttsPeople)
                            playTTS()
                            SpUtils.setTtsPeople(ttsPeople)
                        }
                    }
                }

                override fun getLayoutId(type: Int): Int {
                    return R.layout.layout_tts_people_list
                }
            })
    }

    //初始化监听
    private fun initListener() {
        //监听
        bar_voice_speed.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                VoiceHelper.setVoiceSpeed(progress.toString())
                SpUtils.setTtsPlaySpeed(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        //监听
        bar_voice_volume.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                VoiceHelper.setVoiceVolume(progress.toString())
                SpUtils.setTtsVolume(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }

    //开始播放
    private fun playTTS() {
        VoiceHelper.ttsStop()
        VoiceHelper.ttsStart(getString(R.string.text_test_tts))
    }
}