package com.example.wsdlg.view.main_fragment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.wsdlg.base.BaseFragment
import com.example.wsdlg.em.EMUtils
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMGroup
import gongren.com.dlg.databinding.FragmentMessageBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MessageFragment :BaseFragment<FragmentMessageBinding>(){
    override fun getBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup?
    ): FragmentMessageBinding {
        return FragmentMessageBinding.inflate(layoutInflater,viewGroup,false)
    }

    override fun initView() {
        /*GlobalScope.launch {
            EMUtils().groupList.forEach {
                Log.d("asdws", "onSuccessGroup: "+it.groupName)
            }
        }*/
        EMClient.getInstance().login("13211928598", "123456", object : EMCallBack {
            override fun onSuccess() {
                Log.d("asdws", "onSuccess: login")
                EMUtils().friendNameList.forEach {
                    Log.d("asdws", "onSuccess: "+it)
                }
                EMUtils().groupList.forEach {
                    Log.d("asdws", "onSuccessGroup: "+it.groupName)
                }

            }

            override fun onError(code: Int, error: String?) {
                Log.d("asdws", "onError: code"+error)

            }

            override fun onProgress(progress: Int, status: String?) {

            }
        })
       /* val allContactsFromServer = EMClient
            .getInstance()
            .contactManager().allContactsFromServer
        allContactsFromServer.forEach {
            Log.d("asdws", "initView: "+it)
        }*/
    }

    override fun initListener() {

    }

    override fun initViewModel() {

    }

    override fun initRequest() {

    }

    override fun initData() {

    }
}