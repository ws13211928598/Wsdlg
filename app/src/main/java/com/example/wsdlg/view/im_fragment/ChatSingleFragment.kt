package com.example.wsdlg.view.im_fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mylibrary.assist.WsViewHolder
import com.example.mylibrary.utils.WsRecyclerAdapterMore
import com.example.wsdlg.MainActivity
import com.example.wsdlg.base.BaseFragment
import com.example.wsdlg.em.EMUtils
import com.hyphenate.EMConversationListener
import com.hyphenate.EMMessageListener
import com.hyphenate.chat.EMMessage
import gongren.com.dlg.R
import gongren.com.dlg.databinding.FragmentChatSingleBinding

class ChatSingleFragment : BaseFragment<FragmentChatSingleBinding>() {
    var TAG = "asdws"
    var emMessageListener: EMMessageListener? = null
    override fun getBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup?
    ): FragmentChatSingleBinding {
        return FragmentChatSingleBinding.inflate(layoutInflater, viewGroup, false)
    }

    override fun initView() {
        initEdit()
        initRecycler()
    }

    private fun initEdit() {
        binding.edit.addTextChangedListener(){
            when(it?.length){
                0->{
                    binding.btnSend.visibility = View.GONE
                }
                else ->{
                    binding.btnSend.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initRecycler() {
        binding.recycler.layoutManager = LinearLayoutManager(activity)
        val itemLayoutList = mutableListOf<Int>()
        EMUtils().sendTextMessage("asdws","17354704125",0)
        emMessageListener = object : EMMessageListener {
            override fun onMessageReceived(messages: MutableList<EMMessage>?) {
                //收到消息
                Log.d(TAG, "onMessageReceived: ")
            }

            override fun onCmdMessageReceived(messages: MutableList<EMMessage>?) {
                //收到透传消息
                Log.d(TAG, "onCmdMessageReceived: ")
            }

            override fun onMessageRead(messages: MutableList<EMMessage>?) {
                //收到已读回执
                Log.d(TAG, "onMessageRead: ")
            }

            override fun onMessageDelivered(messages: MutableList<EMMessage>?) {
                //收到已送达回执
                Log.d(TAG, "onMessageDelivered: ")

            }

            override fun onMessageRecalled(messages: MutableList<EMMessage>?) {
                //消息被撤回
                Log.d(TAG, "onMessageRecalled: ")
            }

            override fun onMessageChanged(message: EMMessage?, change: Any?) {
                //消息状态变动
                Log.d(TAG, "onMessageChanged: ")
            }
        }
        EMUtils().acceptMessageMonitoring(emMessageListener)
    }
    /*binding.recycler.adapter = object : WsRecyclerAdapterMore(requireActivity(),itemLayoutList){
        override fun WsBindViewHolder(holder: WsViewHolder, position: Int) {

        }

        override fun WsItemCount(): Int {

        }

        override fun WsgetItemViewType(position: Int): Int {

        }

    }
}*/

    override fun initListener() {
        binding.ivBack.setOnClickListener {
            MainActivity.navController!!.navigateUp()
        }
    }

    override fun initViewModel() {

    }

    override fun initRequest() {

    }

    override fun initData() {

    }

    override fun onDestroy() {
        super.onDestroy()
        //页面销毁要将监听销毁
        EMUtils().removeMessageListener(emMessageListener)
    }
}