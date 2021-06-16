package com.dlg.network.interceptor


import com.blankj.utilcode.util.SPUtils
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * GlobalHeaderInterceptor
 * @author Edwin Wu
 * @since 1.0.0
 */
class GlobalHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()

        builder.header("Content-Type", "application/x-www-form-urlencoded")
        builder.header("channel", "android")
        builder.header("user-agent", "android")

        val token:String = SPUtils.getInstance().getString("token")
        if (token.isEmpty()) {
            builder.header("Authorization", "admin")
        } else {
            builder.header("Authorization", "Bearer ${token}")
        }

        val requestBuild: Request = builder.build()
        return chain.proceed(requestBuild)
    }
}