package com.bobomee.module_assistant.error


/**
 * Profile: 语音错误码
 */
object VoiceErrorCode {

    //1.网络超时

    //DNS连接超时
    private const val CODE_DNS_CONNECT_TIME_OUT = 1000

    //网络连接超时
    private const val CODE_NET_CONNECT_TIME_OUT = 1001

    //网络读取超时
    private const val CODE_NET_READ_TIME_OUT = 1002

    //上行网络连接超时
    private const val CODE_NET_UP_CONNECT_TIME_OUT = 1003

    //上行网络读取超时
    private const val CODE_NET_UP_READ_TIME_OUT = 1004

    //下行网络连接超时
    private const val CODE_NET_DOWN_CONNECT_TIME_OUT = 1005

    //下行网络读取超时
    private const val CODE_NET_DOWN_READ_TIME_OUT = 1006

    //2.网络连接失败

    //网络连接失败
    private const val CODE_NET_CONNECT_FAIL = 2000

    //网络读取失败
    private const val CODE_NET_READ_FAIL = 2001

    //上行网络连接失败
    private const val CODE_NET_UP_CONNECT_FAIL = 2002

    //上行网络读取失败
    private const val CODE_NET_UP_READ_FAIL = 2003

    //下行网络连接失败
    private const val CODE_NET_DOWN_CONNECT_FAIL = 2004

    //下行网络读取失败
    private const val CODE_NET_DOWN_READ_FAIL = 2005

    //下行数据异常
    private const val CODE_DATA_DOWN_EXCEPTION = 2006

    //本地网络不可用
    private const val CODE_NET_LOCAL_DISABLED = 2100

    //3.音频错误

    //录音机打开失败
    private const val CODE_AUDIO_OPEN_FAIL = 3001

    //录音机参数错误
    private const val CODE_AUDIO_PARAMS_ERROR = 3002

    //录音机不可用
    private const val CODE_AUDIO_DISABLED = 3003

    //录音机读取失败
    private const val CODE_AUDIO_READ_FAIL = 3006

    //录音机关闭失败
    private const val CODE_AUDIO_CLOSE_FAIL = 3007

    //文件打开失败
    private const val CODE_AUDIO_FILE_OPEN_FAIL = 3008

    //文件读取失败
    private const val CODE_AUDIO_FILE_READ_FAIL = 3009

    //文件关闭失败
    private const val CODE_AUDIO_FILE_CLOSE_FAIL = 3010

    //VAD异常，通常是VAD资源设置不正确
    private const val CODE_AUDIO_VAD_EXCEPTION = 3100

    //长时间未检测到人说话，请重新识别
    private const val CODE_AUDIO_LONG_TIME_NO_SPEAK = 3101

    //检测到人说话，但语音过短
    private const val CODE_AUDIO_SPEAK_SHORT = 3102

    //4.协议错误

    //协议出错
    private const val CODE_PROTOCAL_ERROR = 4001

    //协议出错
    private const val CODE_AUDIO_PROTOCAL_ERROR = 4002

    //识别出错
    private const val CODE_ASR_ERROR = 4003

    //鉴权错误
    private const val CODE_AAA_ERROR = 4004


    //5.客户端调用错误

    //无法加载so库
    private const val CODE_CLIENT_NOT_LOAD_JNI = 5001

    //识别参数有误
    private const val CODE_CLIENT_ASR_PARAMS_ERROR = 5002

    //获取token失败
    private const val CODE_CLIENT_GET_TOKEN_ERROR = 5003

    //客户端DNS解析失败
    private const val CODE_CLIENT_DNS_PARSING_ERROR = 5004

    //其他
    private const val CODE_CLIENT_OTHER_ERROR = 5005

    //6.超时

    //未开启长语音时，当输入语音超过60s时，会报此错误
    private const val CODE_LONG_AUDIO_TIME_OUT = 6001

    //7.没有识别结果

    //没有匹配的识别结果
    private const val CODE_ASR_NO_RESULT = 7001

    //8.引擎忙

    //识别引擎繁忙
    private const val CODE_ASR_BUSY = 8001

    //9.缺少权限

    //缺少录音权限
    private const val CODE_AUDO_NOT_PERMISSION = 9001

    //10.其他错误

    //离线引擎异常
    private const val CODE_ASR_OFFLINE_EXCEPTION = 10001

    //没有授权文件
    private const val CODE_ASR_NOT_AUTHORIZATION_FILE = 10002

    //授权文件不可用
    private const val CODE_ASR_AUTHORIZATION_FILE_DISABLED = 10003

    //离线参数设置错误
    private const val CODE_ASR_OFFLINE_PARAMS_SET_ERROR = 10004

    //引擎没有被初始化
    private const val CODE_ASR_NOT_INIT = 10005

    //模型文件不可用
    private const val CODE_ASR_MODULE_FILE_DISABLED = 10006

    //语法文件不可用
    private const val CODE_ASR_GRAMMAR_FILE_DISABLED = 10007

    //引擎重置失败
    private const val CODE_ASR_RESET_INIT_ERROR = 10008

    //引擎初始化失败
    private const val CODE_ASR_INIT_ERROR = 10009

    //引擎释放失败
    private const val CODE_ASR_RELEASE_ERROR = 10010

    //引擎不支持
    private const val CODE_ASR_NOT_SUPPORT = 10011

    //离线引擎识别失败
    private const val CODE_ASR_OFFLINE_PARSING_FAIL = 10012

    //4004.子错误值

    //pv超限
    private const val CODE_APP_PV_NOT = 4

    //没勾权限
    private const val CODE_APP_NOT_EXIST = 6

    //qps超限
    private const val CODE_APP_QPS_NOT = 13

    //key错误
    private const val CODE_APP_KEY_ERROR = 101

    //10.录音设备出错

    //录音设备异常
    private const val CODE_AUDIO_DEVICES_EXCEPTION = 1

    //无录音权限
    private const val CODE_AUDIO_DEVICES_NOT_PERMISSION = 2

    //录音设备不可用
    private const val CODE_AUDIO_DEVICES_DISABLED = 3

    //录音中断
    private const val CODE_AUDIO_DEVICES_INTERRUPT = 4

    //11.唤醒相关错误

    //没有授权文件
    private const val CODE_WAKEUP_NOT_AUTHORIZATION_FILE = 11002

    //授权文件不可用
    private const val CODE_WAKEUP_AUTHORIZATION_FILE_DISABLED = 11003

    //唤醒异常
    private const val CODE_WAKEUP_EXCEPTION = 11004

    //模型文件不可用
    private const val CODE_WAKEUP_MODULE_FILE_DISABLED = 11005

    //引擎初始化失败
    private const val CODE_WAKEUP_INIT_ERROR = 11006

    //内存分配失败
    private const val CODE_WAKEUP_MEMOEY_ALLCATION_FAIL = 11007

    //引擎重置失败
    private const val CODE_WAKEUP_RESET_INIT_FAIL = 11008

    //引擎释放失败
    private const val CODE_WAKEUP_RELEASE_FAIL = 11009

    //引擎不支持该架构
    private const val CODE_WAKEUP_NOT_SUPPORT = 11010

    //38.引擎错误

    //唤醒引擎异常
    private const val CODE_ENGINE_WAKE_ENGINE_EXCEPTION = 1

    //无授权文件
    private const val CODE_ENGINE_NOT_AUTHORIZATION_FILE = 2

    //授权文件异常
    private const val CODE_ENGINE_AUTHORIZATION_FILE_EXCEPTION = 3

    //唤醒异常
    private const val CODE_ENGINE_WAKE_EXCEPTION = 4

    //模型文件异常
    private const val CODE_ENGINE__MODULE_FILE_EXCEPTION = 5

    //引擎初始化失败
    private const val CODE_ENGINE_INIT_FAIL = 6

    //内存分配失败
    private const val CODE_ENGINE_MEMOEY_ALLCATION_FAIL = 7

    //引擎重置失败
    private const val CODE_ENGINE_RESET_INIT_FAIL = 8

    //引擎释放失败
    private const val CODE_ENGINE_RELEASE_FAIL = 9

    //引擎不支持该架构
    private const val CODE_ENGINE_NOT_SUPPORT = 10

    //无识别数据
    private const val CODE_ENGINE_NOT_DATA = 11

    //将错误码转换成语义文本
    fun fixErrorCode(errorCode: Int): String {
        return when (errorCode) {
            CODE_DNS_CONNECT_TIME_OUT -> "DNS连接超时"
            CODE_NET_CONNECT_TIME_OUT -> "网络连接超时"
            CODE_NET_READ_TIME_OUT -> "网络读取超时"
            CODE_NET_UP_CONNECT_TIME_OUT -> "上行网络连接超时"
            CODE_NET_UP_READ_TIME_OUT -> "上行网络读取超时"
            CODE_NET_DOWN_CONNECT_TIME_OUT -> "下行网络连接超时"
            CODE_NET_DOWN_READ_TIME_OUT -> "下行网络读取超时"
            CODE_NET_CONNECT_FAIL -> "网络连接失败"
            CODE_NET_READ_FAIL -> "网络读取失败"
            CODE_NET_UP_CONNECT_FAIL -> "上行网络连接失败"
            CODE_NET_UP_READ_FAIL -> "上行网络读取失败"
            CODE_NET_DOWN_CONNECT_FAIL -> "下行网络连接失败"
            CODE_NET_DOWN_READ_FAIL -> "下行网络读取失败"
            CODE_DATA_DOWN_EXCEPTION -> "下行数据异常"
            CODE_NET_LOCAL_DISABLED -> "本地网络不可用"
            CODE_AUDIO_OPEN_FAIL -> "录音机打开失败"
            CODE_AUDIO_PARAMS_ERROR -> "录音机参数错误"
            CODE_AUDIO_DISABLED -> "录音机不可用"
            CODE_AUDIO_READ_FAIL -> "录音机读取失败"
            CODE_AUDIO_CLOSE_FAIL -> "录音机关闭失败"
            CODE_AUDIO_FILE_OPEN_FAIL -> "文件打开失败"
            CODE_AUDIO_FILE_READ_FAIL -> "文件读取失败"
            CODE_AUDIO_FILE_CLOSE_FAIL -> "文件关闭失败"
            CODE_AUDIO_VAD_EXCEPTION -> "VAD异常"
            CODE_AUDIO_LONG_TIME_NO_SPEAK -> "你不说话，那我就退下了"
            CODE_AUDIO_SPEAK_SHORT -> "主人说话时间太短了"
            CODE_PROTOCAL_ERROR -> "协议出错"
            CODE_AUDIO_PROTOCAL_ERROR -> "协议出错"
            CODE_ASR_ERROR -> "识别出错"
            CODE_AAA_ERROR -> "鉴权错误"
            CODE_CLIENT_NOT_LOAD_JNI -> "无法加载so库"
            CODE_CLIENT_ASR_PARAMS_ERROR -> "识别参数有误"
            CODE_CLIENT_GET_TOKEN_ERROR -> "获取token失败"
            CODE_CLIENT_DNS_PARSING_ERROR -> "客户端DNS解析失败"
            CODE_CLIENT_OTHER_ERROR -> "其他错误"
            CODE_LONG_AUDIO_TIME_OUT -> "超过一分钟了"
            CODE_ASR_NO_RESULT -> "没有匹配的识别结果"
            CODE_ASR_BUSY -> "识别引擎繁忙"
            CODE_AUDO_NOT_PERMISSION -> "缺少录音权限"
            CODE_ASR_OFFLINE_EXCEPTION -> "离线引擎异常"
            CODE_ASR_NOT_AUTHORIZATION_FILE -> "没有授权文件"
            CODE_ASR_AUTHORIZATION_FILE_DISABLED -> "授权文件不可用"
            CODE_ASR_OFFLINE_PARAMS_SET_ERROR -> "离线参数设置错误"
            CODE_ASR_NOT_INIT -> "引擎没有被初始化"
            CODE_ASR_MODULE_FILE_DISABLED -> "模型文件不可用"
            CODE_ASR_GRAMMAR_FILE_DISABLED -> "语法文件不可用"
            CODE_ASR_RESET_INIT_ERROR -> "引擎重置失败"
            CODE_ASR_INIT_ERROR -> "引擎初始化失败"
            CODE_ASR_RELEASE_ERROR -> "引擎释放失败"
            CODE_ASR_NOT_SUPPORT -> "引擎不支持"
            CODE_ASR_OFFLINE_PARSING_FAIL -> "离线引擎识别失败"
            CODE_APP_PV_NOT -> "pv超限,请购买"
            CODE_APP_NOT_EXIST -> "没勾权限"
            CODE_APP_QPS_NOT -> "qps超限,请购买"
            CODE_APP_KEY_ERROR -> "key错误"
            CODE_AUDIO_DEVICES_EXCEPTION -> "录音设备异常"
            CODE_AUDIO_DEVICES_NOT_PERMISSION -> "无录音权限"
            CODE_AUDIO_DEVICES_DISABLED -> "录音设备不可用"
            CODE_AUDIO_DEVICES_INTERRUPT -> "录音中断"
            CODE_WAKEUP_NOT_AUTHORIZATION_FILE -> "没有授权文件"
            CODE_WAKEUP_AUTHORIZATION_FILE_DISABLED -> "授权文件不可用"
            CODE_WAKEUP_EXCEPTION -> "唤醒异常"
            CODE_WAKEUP_MODULE_FILE_DISABLED -> "模型文件不可用"
            CODE_WAKEUP_INIT_ERROR -> "引擎初始化失败"
            CODE_WAKEUP_MEMOEY_ALLCATION_FAIL -> "内存分配失败"
            CODE_WAKEUP_RESET_INIT_FAIL -> "引擎重置失败"
            CODE_WAKEUP_RELEASE_FAIL -> "引擎释放失败"
            CODE_WAKEUP_NOT_SUPPORT -> "引擎不支持该架构"
            CODE_ENGINE_WAKE_ENGINE_EXCEPTION -> "唤醒引擎异常"
            CODE_ENGINE_NOT_AUTHORIZATION_FILE -> "无授权文件"
            CODE_ENGINE_AUTHORIZATION_FILE_EXCEPTION -> "授权文件异常"
            CODE_ENGINE_WAKE_EXCEPTION -> "唤醒异常"
            CODE_ENGINE__MODULE_FILE_EXCEPTION -> "模型文件异常"
            CODE_ENGINE_INIT_FAIL -> "引擎初始化失败"
            CODE_ENGINE_MEMOEY_ALLCATION_FAIL -> "内存分配失败"
            CODE_ENGINE_RESET_INIT_FAIL -> "引擎重置失败"
            CODE_ENGINE_RELEASE_FAIL -> "引擎释放失败"
            CODE_ENGINE_NOT_SUPPORT -> "引擎不支持该架构"
            CODE_ENGINE_NOT_DATA -> "无识别数据"
            else -> "未知错误"
        }
    }
}