package com.bobomee.module_joke

import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.bobomee.aidl.callback.OnTTSResultListener
import com.bobomee.lib_base.base.view.BaseActivity
import com.bobomee.lib_base.base.adapter.CommonAdapter
import com.bobomee.lib_base.base.adapter.CommonViewHolder
import com.bobomee.lib_base.helper.ARouterHelper
import com.bobomee.lib_base.helper.VoiceHelper
import com.bobomee.lib_base.utils.LogUtils
import com.bobomee.lib_base.utils.SpUtils
import com.bobomee.lib_network.HttpManager
import com.bobomee.lib_network.bean.Data
import com.bobomee.lib_network.bean.JokeListData
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.activity_joke.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Profile: 笑话
 */
@Route(path = ARouterHelper.PATH_JOKE)
class JokeActivity : BaseActivity(), OnRefreshListener, OnRefreshLoadMoreListener {

    //页码
    private var currentPage = 1

    //数据源
    private val mList = ArrayList<Data>()
    private lateinit var mJokeAdapter: CommonAdapter<Data>

    //当前播放的下标
    private var currentPlayIndex = -1

    override fun getLayoutId(): Int {
        return R.layout.activity_joke
    }

    override fun getTitleText(): String {
        return getString(R.string.app_title_joke)
    }

    override fun isShowBack(): Boolean {
        return true
    }

    override fun initView() {

        initList()

        loadData(0)
    }

    //加载数据 0:下拉 1：上拉
    private fun loadData(orientation: Int) {
        HttpManager.queryJokeList(currentPage, object : Callback<JokeListData> {
            override fun onFailure(call: Call<JokeListData>, t: Throwable) {
                LogUtils.i("onFailure$t")
                if (orientation == 0) {
                    refreshLayout.finishRefresh()
                } else if (orientation == 1) {
                    refreshLayout.finishLoadMore()
                }
            }

            override fun onResponse(call: Call<JokeListData>, response: Response<JokeListData>) {
                LogUtils.i("onResponse")
                if (orientation == 0) {
                    refreshLayout.finishRefresh()
                    //列表要清空
                    mList.clear()
                    response.body()?.result?.data?.let { mList.addAll(it) }
                    //适配器要全部刷新
                    mJokeAdapter.notifyDataSetChanged()
                } else if (orientation == 1) {
                    refreshLayout.finishLoadMore()
                    //追加在尾部
                    response.body()?.result?.data?.let {
                        //上一次的最大值
                        val positionStart = mList.size
                        mList.addAll(it)
                        //局部刷新
                        mJokeAdapter.notifyItemRangeInserted(positionStart, it.size)
                    }
                }
            }
        })
    }

    //初始化列表
    private fun initList() {

        //刷新组件
        refreshLayout.setRefreshHeader(ClassicsHeader(this))
        refreshLayout.setRefreshFooter(ClassicsFooter(this))

        //监听
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.setOnRefreshLoadMoreListener(this)

        mJokeListView.layoutManager = LinearLayoutManager(this)
        mJokeAdapter = CommonAdapter(mList, object : CommonAdapter.OnBindDataListener<Data> {
            override fun onBindViewHolder(
                model: Data,
                viewHolder: CommonViewHolder,
                type: Int,
                position: Int
            ) {
                //内容
                viewHolder.setText(R.id.tvContent, model.content)

                //播放按钮
                val tvPlay = viewHolder.getView(R.id.tvPlay) as TextView
                //设置文本
                tvPlay.text =
                    if (currentPlayIndex == position) getString(R.string.app_media_pause) else getString(
                        R.string.app_media_play
                    )

                //点击事件
                tvPlay.setOnClickListener {
                    //比想象中更加复杂
                    //说明点击了正在播放的下标
                    if (currentPlayIndex == position) {
                        VoiceHelper.ttsPause()
                        currentPlayIndex = -1
                        tvPlay.text = getString(R.string.app_media_play)
                    } else {
                        val oldIndex = currentPlayIndex
                        currentPlayIndex = position
                        VoiceHelper.ttsStop()
                        VoiceHelper.registerTTSListener(object : OnTTSResultListener() {
                            override fun onSpeechFinish(var1: String?) {
                                super.onSpeechFinish(var1)
                                currentPlayIndex = -1
                                tvPlay.text = getString(R.string.app_media_play)
                            }
                        })
                        VoiceHelper.ttsStart(model.content)
                        tvPlay.text = getString(R.string.app_media_pause)
                        //刷新旧的
                        mJokeAdapter.notifyItemChanged(oldIndex)
                    }
                }
            }

            override fun getLayoutId(type: Int): Int {
                return R.layout.layout_joke_list_item
            }
        })
        mJokeListView.adapter = mJokeAdapter
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        currentPage = 1
        loadData(0)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        currentPage++
        loadData(1)
    }

    override fun onDestroy() {
        super.onDestroy()
        val isJoke = SpUtils.getBoolean("isJoke", true)
        if (!isJoke) {
            VoiceHelper.ttsStop()
        }
    }
}