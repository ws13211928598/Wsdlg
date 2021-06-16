package com.example.lib_network.bean

import com.hyphenate.chat.EMGroup


/**
 *  created by ws
 *   on 2021/6/4
 *   describe:
 */
data class UserMessageBean(
    var userId:Int = -1,
    var login: Boolean = false,
    var clickOn:Boolean = true,
    var phoneNumber:String = "",
    var userFriendBaseBean: UserFriendBaseBean ?= null,
    var userGroupList:MutableList<EMGroup> = mutableListOf(),
)