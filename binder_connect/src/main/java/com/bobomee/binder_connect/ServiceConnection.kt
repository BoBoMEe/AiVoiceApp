package com.bobomee.binder_connect

import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import android.os.IInterface
import android.os.SystemClock
import com.bobomee.aidl.IAsrManager
import com.bobomee.aidl.IBinderPool
import com.bobomee.aidl.ITTSManager
import com.bobomee.aidl.IWakeUpManager

class ServiceConnection {
    private var mContext: Context? = null
    private var mBinderPool: IBinderPool? = null
    private var mDestroyByUser = false
    private var mStarted = false
    private var mConnected = false
    private val mServiceMap by lazy { mutableMapOf<String, IInterface?>() }
    private val mActivityIntent by lazy {
        val activityIntent = Intent()
        val intentPkg = ServiceTools.getIntentPkg()
        val activityClz = ServiceTools.getActivityClz()
        val componentName = ComponentName(intentPkg,activityClz)
        activityIntent.component = componentName
        val activityAct = ServiceTools.getActivityAct()
        if (activityAct.isNotEmpty()) {
            activityIntent.action = activityAct
        }
        activityIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        activityIntent
    }
    private val mServiceIntent by lazy {
        val intent = Intent()
        val intentPkg = ServiceTools.getIntentPkg()
        val serviceClz = ServiceTools.getServiceClz()
        intent.component = ComponentName(intentPkg,serviceClz)
        val serviceAct = ServiceTools.getServiceAct()
        if (serviceAct.isNotEmpty()){
            intent.action = serviceAct
        }
        intent
    }

    fun init(){
        mContext = ServiceTools.getContext()
    }

    private val mServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            mConnected = false
            mServiceMap.clear()
            if (!mDestroyByUser) {
                bindService()
            }
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mBinderPool = IBinderPool.Stub.asInterface(service)
            mConnected = true
        }

    }

    fun startService(){
        mStarted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                mContext?.startService(mServiceIntent)
            } catch (e: Exception) {
                mContext?.startActivity(mActivityIntent)
            }
            true
        } else {
            mContext?.startService(mServiceIntent)
            true
        }
    }

    fun bindService() {
        if (!checkAndBindService()){
            startAndBindService()
        }
    }

    private fun startAndBindService(){
        startService()
        SystemClock.sleep(300)
        checkAndBindService()
    }

    private fun checkAndBindService() :Boolean {
        if (mStarted) {
            mContext?.bindService(mServiceIntent, mServiceConnection, BIND_AUTO_CREATE)
        }
        return mStarted
    }

    fun unbindService() {
        mDestroyByUser = true
        mContext?.unbindService(mServiceConnection)
        mBinderPool = null
    }

    fun findService(tag: String): IInterface? {
        return if (mServiceMap.containsKey(tag)) {
            mServiceMap[tag]
        } else {
            mServiceMap[tag] = findInternal(tag)
            if (mServiceMap[tag] == null) {
                mServiceMap.remove(tag)
            } else {
                mServiceMap[tag]
            }
        }
    }

    private fun findInternal(tag: String): IInterface? {
        return when (tag) {
            ServiceTag.ASR -> {
                IAsrManager.Stub.asInterface(mBinderPool?.queryBinder(tag))
            }
            ServiceTag.TTS -> {
                ITTSManager.Stub.asInterface(mBinderPool?.queryBinder(tag))
            }
            ServiceTag.WAKE_UP -> {
                IWakeUpManager.Stub.asInterface(mBinderPool?.queryBinder(tag))
            }
            else -> null
        }
    }

    fun isConnected(): Boolean {
        return mConnected
    }

    fun isStarted():Boolean{
        return mStarted
    }
}