package com.bobomee.module_developer

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.bobomee.lib_base.base.view.BaseActivity
import com.bobomee.lib_base.base.adapter.CommonAdapter
import com.bobomee.lib_base.base.adapter.CommonViewHolder
import com.bobomee.lib_base.helper.ARouterHelper
import com.bobomee.lib_base.helper.VoiceHelper
import com.bobomee.module_developer.data.DeveloperListData
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import kotlinx.android.synthetic.main.activity_developer.*

/**
 * Profile: 开发者模式
 */
@Route(path = ARouterHelper.PATH_DEVELOPER)
class DeveloperActivity : BaseActivity() {

    //标题
    private val mTypeTitle = 0

    //内容
    private val mTypeContent = 1

    private val mList = ArrayList<DeveloperListData>()

    override fun getLayoutId(): Int {
        return R.layout.activity_developer
    }

    override fun getTitleText(): String {
        return title.toString()
    }

    override fun isShowBack(): Boolean {
        return true
    }

    override fun initView() {

        AndPermission.with(this)
            .runtime()
            .permission(Permission.RECORD_AUDIO)
            .start()

        initData()
        initListView()
    }

    //初始化数据
    private fun initData() {
        val dataArray = resources.getStringArray(R.array.DeveloperListArray)
        dataArray.forEach {
            //说明是标题
            if (it.contains("[")) {
                addItemData(mTypeTitle, it.replace("[", "").replace("]", ""))
            } else {
                addItemData(mTypeContent, it)
            }
        }
    }

    //初始化列表
    private fun initListView() {
        //布局管理器
        rvDeveloperView.layoutManager = LinearLayoutManager(this)
        //适配器
        rvDeveloperView.adapter = CommonAdapter(mList, object :
            CommonAdapter.OnMoreBindDataListener<DeveloperListData> {
            override fun onBindViewHolder(
                model: DeveloperListData,
                viewHolder: CommonViewHolder,
                type: Int,
                position: Int
            ) {
                when (model.type) {
                    mTypeTitle -> viewHolder.setText(R.id.mTvDeveloperTitle, model.text)
                    mTypeContent -> {
                        viewHolder.setText(R.id.mTvDeveloperContent, "${position}.${model.text}")
                        viewHolder.itemView.setOnClickListener {
                            itemClickFun(position)
                        }
                    }
                }
            }

            override fun getLayoutId(type: Int): Int {
                return if (type == mTypeTitle) {
                    R.layout.layout_developer_title
                } else {
                    R.layout.layout_developer_content
                }
            }

            override fun getItemType(position: Int): Int {
                return mList[position].type
            }
        })
    }

    //添加数据
    private fun addItemData(type: Int, text: String) {
        mList.add(DeveloperListData(type, text))
    }

    //点击事件
    private fun itemClickFun(position: Int) {
        when (position) {
            1 -> ARouterHelper.startActivity(ARouterHelper.PATH_JOKE)
            2 -> ARouterHelper.startActivity(ARouterHelper.PATH_MAP)
            3 -> ARouterHelper.startActivity(ARouterHelper.PATH_SETTING)
            4 -> ARouterHelper.startActivity(ARouterHelper.PATH_VOICE_SETTING)
            5 -> ARouterHelper.startActivity(ARouterHelper.PATH_WEATHER)

            7 -> VoiceHelper.startAsr()
            8 -> VoiceHelper.stopAsr()
            9 -> VoiceHelper.cancelAsr()
            10 -> VoiceHelper.releaseAsr()

            12 -> VoiceHelper.startWakeUp()
            13 -> VoiceHelper.stopWakeUp()

            18 -> VoiceHelper.ttsStart(getString(R.string.text_tts_play))
            19 -> VoiceHelper.ttsPause()
            20 -> VoiceHelper.ttsResume()
            21 -> VoiceHelper.ttsStop()
            22 -> VoiceHelper.ttsRelease()
        }
    }
}