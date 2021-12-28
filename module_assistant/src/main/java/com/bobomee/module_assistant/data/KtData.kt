package com.bobomee.module_assistant.data

data class MainListData(
    val title: String,
    val desc: String,
    val icon: Int
)

/**
 * type：会话类型
 * text：文本
 */
data class ChatList(
    val type: Int
) {
    lateinit var text: String

    //天气
    lateinit var wid: String
    lateinit var info: String
    lateinit var city: String
    lateinit var temperature: String
    lateinit var start: String
    lateinit var end: String
    lateinit var air: String
}


//联系人
data class ContactData(
    val phoneName: String,
    val phoneNumber: String
)