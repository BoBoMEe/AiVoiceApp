package com.bobomee.lib_base.helper

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat


/**
 * Profile: 通知栏的帮助类
 */
object NotificationHelper {

    private lateinit var mContext: Context
    private lateinit var nm: NotificationManager

    private const val CHANNEL_ID = "ai_voice_service"
    private const val CHANNEL_NAME = "语音服务"
    private const val CHANNEL_CONTENT = "语音服务正在运行"

    private const val CHANNEL_POOL_ID = "binder_pool_service"
    private const val CHANNEL_POOL_NAME = "远程服务"
    private const val CHANNEL_POOL_CONTENT = "远程服务正在运行"

    //初始化帮助类
    fun initHelper(mContext: Context) {
        this.mContext = mContext

        nm = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //创建渠道
        setBindVoiceChannel()
        setBindPoolChannel()
    }

    //设置绑定服务的渠道
    private fun setBindVoiceChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //创建渠道对象
            val channel =
                NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            //呼吸灯
            channel.enableLights(false)
            //震动
            channel.enableVibration(false)
            //角标
            channel.setShowBadge(false)
            nm.createNotificationChannel(channel)
        }
    }

    //设置绑定服务的渠道
    private fun setBindPoolChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //创建渠道对象
            val channel =
                NotificationChannel(
                    CHANNEL_POOL_ID,
                    CHANNEL_POOL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                )
            //呼吸灯
            channel.enableLights(false)
            //震动
            channel.enableVibration(false)
            //角标
            channel.setShowBadge(false)
            nm.createNotificationChannel(channel)
        }
    }

    //绑定语音服务
    fun bindVoiceService(): Notification {
        //创建通知栏对象
        val notificationCompat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder(mContext, CHANNEL_ID)
        } else {
            NotificationCompat.Builder(mContext)
        }
        //设置标题
        notificationCompat.setContentTitle(CHANNEL_NAME)
        //设置描述
        notificationCompat.setContentText(CHANNEL_CONTENT)
        //设置时间
        notificationCompat.setWhen(System.currentTimeMillis())
        //禁止滑动
        notificationCompat.setAutoCancel(false)
        return notificationCompat.build()
    }

    //绑定初始化服务
    fun bindPoolService(): Notification {
        //创建通知栏对象
        val notificationCompat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder(mContext, CHANNEL_POOL_ID)
        } else {
            NotificationCompat.Builder(mContext)
        }
        //设置标题
        notificationCompat.setContentTitle(CHANNEL_POOL_NAME)
        //设置描述
        notificationCompat.setContentText(CHANNEL_POOL_CONTENT)
        //设置时间
        notificationCompat.setWhen(System.currentTimeMillis())
        //禁止滑动
        notificationCompat.setAutoCancel(false)
        return notificationCompat.build()
    }
}