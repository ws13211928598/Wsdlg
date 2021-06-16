package com.example.wsdlg.view_model

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.lib_network.NetWork
import com.example.lib_network.bean.*
import com.example.wsdlg.MainActivity
import com.example.wsdlg.MainActivity.Companion.mainViewModel
import com.example.wsdlg.utils.toast
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlin.collections.HashMap

/**
 *  created by ws
 *   on 2021/6/4
 *   describe:
 */
class MainViewModel : ViewModel() {
    val mediatorLiveData = MediatorLiveData<UserMessageBean>()
    val successLiveData = MediatorLiveData<Any>()
    var head = "api"

    init {
        mediatorLiveData.value = UserMessageBean()
        successLiveData.value = null
    }

    fun request(name: String, map: HashMap<String, Any>) {
        when (name) {
            //发送验证码
            "send_code" -> requestSendCode(map)
            //验证码登录
            "verify_code" -> requestVerifyCode(map)
            //获取好友列表
            "user_friends" -> requestUserFriends()
        }
    }

    private fun requestUserFriends() {
        NetWork.acceptData(
            NetWork.requestData(head)
                .userFriends()
        ).subscribe(object : Observer<BaseResponse<UserFriendBaseBean>> {
            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(t: BaseResponse<UserFriendBaseBean>) {
                Log.d("asdws", "onNext: ab")
                val value = mainViewModel.mediatorLiveData.value
                value?.userFriendBaseBean = t.data
                mainViewModel.mediatorLiveData.value = value
            }

            override fun onError(e: Throwable) {
                Log.d("asdws", "onError: ")
            }

            override fun onComplete() {
            }

        })
    }

    private fun requestVerifyCode(map: java.util.HashMap<String, Any>) {
        NetWork.acceptData(
            NetWork.requestData(head)
                .verifyCode(map)
        ).subscribe(object : Observer<BaseResponse<UserLoginMode>> {
            override fun onSubscribe(d: Disposable) {
                Log.d("asdws", "onSubscribe: ")
            }

            override fun onNext(t: BaseResponse<UserLoginMode>) {
                Log.d("asdws", "onNext: ")
                MainActivity.mainViewModel.successLiveData.value = t.data
            }

            override fun onError(e: Throwable) {
                Log.d("asdws", "onError: ")
                e.message?.toast()
            }

            override fun onComplete() {
                Log.d("asdws", "onComplete: ")
                MainActivity.mainViewModel?.successLiveData?.value = null
            }

        })
    }


    //发送验证码
    fun requestSendCode(map: HashMap<String, Any>) {
        NetWork.acceptData(
            NetWork.requestData(head)
                .sendCode(map)
        ).subscribe(object : Observer<BaseResponse<SendCodeBean>> {
            override fun onSubscribe(d: Disposable) {
                mediatorLiveData.value!!.clickOn = false
                Log.d("asdws", "onSubscribe: ")
            }

            override fun onNext(t: BaseResponse<SendCodeBean>) {
                mediatorLiveData.value!!.clickOn = true
                Log.d("asdws", "onNext: ")
                successLiveData.value = t.data

            }

            override fun onError(e: Throwable) {
                mediatorLiveData.value!!.clickOn = true
                e.message?.toast()
                Log.d("asdws", "onError: " + e.message)
            }

            override fun onComplete() {
                Log.d("asdws", "onComplete: ")
                MainActivity.mainViewModel?.successLiveData?.value = null
            }


        })


    }
}