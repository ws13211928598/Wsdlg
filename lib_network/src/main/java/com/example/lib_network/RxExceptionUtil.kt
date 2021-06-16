package com.example.lib_network

import org.json.JSONException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

/**
 * 异常处理
 */
object RxExceptionUtil {
    @JvmStatic
    fun exceptionHandler(e: Throwable): String {
        var errorMsg = "未知错误"
        if (e is UnknownHostException) {
            errorMsg = "网络不可用"
        } else if (e is SocketTimeoutException) {
            errorMsg = "请求网络超时"
        } else if (e is HttpException) {
            errorMsg = convertStatusCode(e)
        } else if (e is ParseException || e is JSONException
            || e is JSONException
        ) {
            errorMsg = "数据解析错误"
        }
        return errorMsg
    }

    private fun convertStatusCode(httpException: HttpException): String {
        val msg: String = when {
            httpException.code() in 500..599 -> {
                "服务器处理请求出错"
            }
            httpException.code() in 400..499 -> {
                "服务器无法处理请求"
            }
            httpException.code() in 300..399 -> {
                "请求被重定向到其他页面"
            }
            else -> {
                httpException.message()
            }
        }
        return msg
    }
}