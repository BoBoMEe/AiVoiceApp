package com.bobomee.module_assistant.helper

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import com.bobomee.module_assistant.data.ContactData
import com.bobomee.lib_base.utils.LogUtils


/**
 * Profile: 联系人帮助类 / 权限
 */
object ContactHelper {

    private lateinit var mContext: Context

    //联系人列表
    val mContactList = ArrayList<ContactData>()

    //数据库地址
    private val PHONE_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI

    //查询条件 名称 - 号码
    private const val NAME = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
    private const val PHONE = ContactsContract.CommonDataKinds.Phone.NUMBER

    //初始化
    fun initHelper(mContext: Context) {
        ContactHelper.mContext = mContext

        loadContact()
    }

    //加载通讯录
    private fun loadContact() {
        val resolver = mContext.contentResolver
        val cursor = resolver.query(
            PHONE_URI, arrayOf(NAME, PHONE),
            null, null, null
        )
        cursor?.let {
            while (it.moveToNext()) {
                val data = ContactData(
                    it.getString(it.getColumnIndex(NAME)),
                    it.getString(it.getColumnIndex(PHONE)).trim()
                )
                mContactList.add(data)
            }
        }
        LogUtils.i("mContactList:$mContactList")
        cursor?.close()
    }

    /**
     * 拨打电话
     */
    @SuppressLint("MissingPermission")
    fun callPhone(phone: String) {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$phone")
        mContext.startActivity(intent)
    }
}