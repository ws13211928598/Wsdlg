package com.example.lib_network.bean

/**
 *  created by ws
 *   on 2021/6/15
 *   describe:
 */


 data class UserFriendBaseBean(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<UserFriendBean>
)

data class UserFriendBean(
    val UserCredibilityValue: String,
    val UserPersonality: Int,
    val UserPriorityValue: String,
    val balance: String,
    val client_type: Int,
    val created_time: String,
    val huanxin_id: String,
    val id: Int,
    val invited_user_id: Any,
    val is_agent: Boolean,
    val is_bind_bank: Boolean,
    val is_import: Boolean,
    val is_member: Int,
    val nowithdrawal: String,
    val ocr_verify: Boolean,
    val password: String,
    val pay_password_set: Boolean,
    val personal_verify: Boolean,
    val service_fee: Int,
    val token: String,
    val type: Int,
    val updated_time: String,
    val userActive: Int,
    val userAddress: String,
    val userBirthday: String,
    val userEmail: String,
    val userGender: Int,
    val userHeight: String,
    val userImageUrl: String,
    val userInviteNum: Int,
    val userNickName: String,
    val userPhoneNumber: String,
    val userRealName: String,
    val userServicePrice: String,
    val userServiceUnitPrice: String,
    val userStatus: Int,
    val userWeight: String,
    val userYanZhi: String,
    val user_employ_status_code: Int,
    val wechat_open_id: String,
    val withdrawal: String
)