package com.example.lib_network

import android.accounts.NetworkErrorException
import com.example.lib_network.RxExceptionUtil.exceptionHandler
import io.reactivex.Observer
import io.reactivex.disposables.Disposable


/**
 * 数据返回统一处理
 * @param <T>
</T> */
abstract class BaseObserver<T> : Observer<BaseResponse<T>> {

    override fun onNext(response: BaseResponse<T>) {
        if (response.isOk()) {
            response.data?.let {
                onSuccess(it, response.msg)
            }
        } else {
            onFailure(NetworkErrorException("服务器处理请求出错"), response.msg)
        }
    }

    override fun onError(e: Throwable) {
        onFailure(e, exceptionHandler(e))
    }

    override fun onComplete() {}
    override fun onSubscribe(d: Disposable) {}

//    abstract fun onSuccess(result: T)

    abstract fun onSuccess(result: T, msg: String)
    abstract fun onFailure(e: Throwable, errorMsg: String)

}