package com.dlg.network.interceptor

import com.blankj.utilcode.util.SPUtils
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * GlobalUrlInterceptor
 * @author Edwin Wu
 * @since 1.0.0
 */
class GlobalUrlInterceptor : Interceptor {
    var version = "3.2.2.1"
    override fun intercept(chain: Interceptor.Chain): Response {
        val uid:String = SPUtils.getInstance().getString("uid")
        if (uid.isEmpty()) {
            return chain.proceed(chain.request())
        }

        val request: Request = chain.request()
        val builder: HttpUrl.Builder = request.url.newBuilder()

        val newUrl: HttpUrl = builder
            .addQueryParameter("uid", uid.toString())
            .addQueryParameter("channel", "android"+version)
            .build()

        val newRequest: Request = request.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}