package com.example.wsdlg.view.main_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.lib_network.bean.UserFriendBaseBean
import com.example.lib_network.bean.UserFriendBean
import com.example.lib_network.bean.UserMessageBean
import com.example.mylibrary.assist.WsViewHolder
import com.example.mylibrary.utils.WsRecyclerAdapterSingle
import com.example.wsdlg.MainActivity
import com.example.wsdlg.base.BaseFragment
import com.example.wsdlg.em.EMUtils
import com.example.wsdlg.utils.delegateutil.viewBinding
import com.hyphenate.EMValueCallBack
import com.hyphenate.chat.EMUserInfo
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.BallPulseFooter
import com.scwang.smartrefresh.layout.header.BezierRadarHeader
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import gongren.com.dlg.R
import gongren.com.dlg.databinding.FragmentFriendBinding
import gongren.com.dlg.databinding.ItemFriendBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FriendFragment : BaseFragment<FragmentFriendBinding>() {
    lateinit var refreshLayout: RefreshLayout
    var friendList: MutableList<UserFriendBean> = mutableListOf()
    override fun getBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup?
    ): FragmentFriendBinding {
        return FragmentFriendBinding.inflate(layoutInflater, viewGroup, false)
    }

    override fun initView() {
        initSmart()
        initRecycler()
        Log.d("asdws", "initView: 222")
        GlobalScope.launch {
            EMUtils().friendNameList.forEach {
                Log.d("asdws", "initView: "+it)
            }
        }
        val mutableListOf = mutableListOf<String>()
        mutableListOf.add("13211928598")
        EMUtils().getUserProperty(mutableListOf.toTypedArray(),
            object : EMValueCallBack<Map<String, EMUserInfo>> {
                override fun onSuccess(value: Map<String, EMUserInfo>?) {
                    Log.d("asdws", "onSuccess: 223")
                    value?.forEach {
                        Log.d("asdws", "onSuccess: "+it.key+"value"+it.value.userId+it.value.phoneNumber)
                    }
                }

                override fun onError(error: Int, errorMsg: String?) {
                    Log.d("asdws", "onError: 223")
                }

            })
    }

    private fun initRecycler() {
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter =
            object : WsRecyclerAdapterSingle(requireActivity(), R.layout.item_friend) {
                override fun WsBindViewHolder(holder: WsViewHolder, position: Int) {
                    holder.viewBinding(ItemFriendBinding::bind).apply {

                        if (position == 0) {
                            holder.itemView.setOnClickListener {

                            }
                        } else {
                            val userFriendBean = friendList[position - 1]
                            this.tvFriendName.text = userFriendBean.userNickName
                            Glide.with(activity!!).load(userFriendBean.userImageUrl).apply(RequestOptions.bitmapTransform(CircleCrop())).into(this.ivFriendIcon)
                            holder.itemView.setOnClickListener {
                                MainActivity.navController?.navigate(R.id.action_mainFragment_to_chatSingleFragment)
                            }

                        }
                    }

                }

                override fun WsItemCount(): Int {
                    return friendList.size + 1
                }
            }
    }

    @SuppressLint("ResourceAsColor")
    private fun initSmart() {
        binding.smart.setRefreshHeader(BezierRadarHeader(activity).setEnableHorizontalDrag(true))
        binding.smart.setRefreshFooter(BallPulseFooter(requireActivity()).setNormalColor(R.color.orange))
        //设置刷新
        binding.smart.setOnRefreshListener {
            refreshLayout = it
            GlobalScope.launch {

                GlobalScope.launch(Dispatchers.Main) {
                    it.finishRefresh()
                    binding.recyclerView.adapter?.notifyDataSetChanged()
                }

            }
            it.finishRefresh(3000)
        }
        //设置加载
        binding.smart.setOnLoadMoreListener {
            refreshLayout = it
            it.finishLoadMore(3000)
        }
    }

    override fun initListener() {

    }

    override fun initViewModel() {
        MainActivity.mainViewModel.mediatorLiveData.observe(this,{
            Log.d("asdws", "initViewModel: ws")
            it.userFriendBaseBean?.let {
                friendList = it.results as MutableList<UserFriendBean>
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }


        })
    }

    override fun initRequest() {
        val mapOf :HashMap<String,Any> = hashMapOf()
        MainActivity.mainViewModel.request("user_friends",mapOf)
    }

    override fun initData() {

    }

}