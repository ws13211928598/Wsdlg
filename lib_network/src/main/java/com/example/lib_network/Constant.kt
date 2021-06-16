package com.example.lib_network

/**
 *  created by ws
 *   on 2021/6/4
 *   describe:
 */
object Constant {
    val BASE_API_URL:String = "https://api2.dalinggong.com/"
    //val BASE_API_URL:String = "http://10.7.121.179:8000/"

    private const val BASE_WEB_URL = "https://cms.dalinggong.com"

    // 打零工用户协议：
    const val H5_URL_AGREEMENT = "$BASE_WEB_URL/static/privacy-agreement-personal.html"

    // 打零工隐私协议：
    const val H5_URL_PRIVACY = "$BASE_WEB_URL/static/privacy-agreement.html"

    const val H5_URL_FEEDBACK = "$BASE_WEB_URL/static/feedback.html?token=%s"
}