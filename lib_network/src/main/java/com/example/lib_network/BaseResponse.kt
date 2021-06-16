package com.example.lib_network

/**
 * @author lgx
 * @date 11/18/20.
 * description：
 */
open class BaseResponse<T> {
    var code = 0
    var msg: String = ""
    var data: T? = null

    fun isOk(): Boolean {
        return code == 200
    }

    override fun toString(): String {
        return "BaseResponse(code=$code, msg='$msg', data=$data)"
    }

}