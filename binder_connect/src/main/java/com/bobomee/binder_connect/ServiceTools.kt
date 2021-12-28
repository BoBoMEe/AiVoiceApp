package com.bobomee.binder_connect

import android.annotation.SuppressLint
import android.content.Context
import android.os.IInterface

@SuppressLint("StaticFieldLeak")
object ServiceTools {
    private lateinit var context:Context
    private var intentPkg: String = ""
    private var serviceClz: String = ""
    private var serviceAct: String = ""
    private var activityClz: String = ""
    private var activityAct: String = ""
    private val mServiceConnection by lazy { ServiceConnection() }

    fun pckage(pkg: () -> String): ServiceTools {
        intentPkg = pkg.invoke()
        return this
    }

    fun activity(clazz: (() -> String), act: (() -> String)? = null): ServiceTools {
        activityClz = clazz.invoke()
        activityAct = act?.invoke()?:""
        return this
    }

    fun service(clazz: (() -> String), act: (() -> String)? = null): ServiceTools {
        serviceClz = clazz.invoke()
        serviceAct = act?.invoke()?:""
        return this
    }

    fun init(context: Context): ServiceTools {
        ServiceTools.context = context.applicationContext
        mServiceConnection.init()
        return this
    }

    fun start(){
        if (serviceClz.isNotEmpty() && intentPkg.isNotEmpty())
            mServiceConnection.startService()
    }

    fun bind(){
        if (serviceClz.isNotEmpty() && intentPkg.isNotEmpty())
            mServiceConnection.bindService()
    }

    fun release() {
        mServiceConnection.unbindService()
    }

    fun <S : IInterface> findService(tag: String): S? {
        val findService = mServiceConnection.findService(tag)
        return if(findService != null) findService as S else null
    }

    fun isStarted():Boolean{
        return mServiceConnection.isStarted()
    }

    fun isConnected(): Boolean {
        return mServiceConnection.isConnected()
    }

    fun getContext():Context{
        return context
    }

    fun getIntentPkg(): String {
        return intentPkg
    }

    fun getServiceClz():String{
        return serviceClz
    }

    fun getServiceAct(): String {
        return serviceAct
    }

    fun getActivityClz():String{
        return activityClz
    }

    fun getActivityAct(): String {
        return activityAct
    }

}