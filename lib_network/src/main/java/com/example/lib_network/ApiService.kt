package com.example.lib_network

import com.example.lib_network.bean.BaseResponse
import com.example.lib_network.bean.SendCodeBean
import com.example.lib_network.bean.UserFriendBaseBean
import com.example.lib_network.bean.UserLoginMode

import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import kotlin.collections.HashMap

/**
 *  created by ws
 *   on 2021/6/4
 *   describe:
 */
interface ApiService {
    //https://api2.dalinggong.com/user/send_code
    @FormUrlEncoded
    @POST("user/send_code")
    fun sendCode(
        @FieldMap map: HashMap<String, Any>
    ): Observable<BaseResponse<SendCodeBean>>

    /**
    * 手机验证登录
    */
    @FormUrlEncoded
    @POST("user/verify_code")
    fun verifyCode(
        @FieldMap map: HashMap<String, Any>
    ): Observable<BaseResponse<UserLoginMode>>


    //获取好友列表
    @GET("user/friends")
    fun userFriends():Observable<BaseResponse<UserFriendBaseBean>>
}