package com.example.wsdlg.view.main

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter

import com.example.wsdlg.base.BaseFragment
import com.example.wsdlg.em.EMUtils

import com.example.wsdlg.utils.BaseUtility
import com.example.wsdlg.view.main_fragment.FriendFragment
import com.example.wsdlg.view.main_fragment.HomeFragment
import com.example.wsdlg.view.main_fragment.MessageFragment
import com.example.wsdlg.view.main_fragment.MyFragment
import gongren.com.dlg.R
import gongren.com.dlg.databinding.FragmentMainBinding


class MainFragment : BaseFragment<FragmentMainBinding>() {

    var context = activity
    override fun getBinding(inflater: LayoutInflater, viewGroup: ViewGroup?): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater, viewGroup, false)
    }

    override fun initView() {
        val fragmentList = mutableListOf<Fragment>(
            HomeFragment(),
            FriendFragment(),
            MessageFragment(),
            MyFragment()
        )
        activity?.let{
            context = activity
            binding.viewpager.adapter = object :FragmentStateAdapter(it){
                override fun getItemCount(): Int {
                    return fragmentList.size
                }

                override fun createFragment(position: Int): Fragment {
                    return fragmentList[position]
                }


            }
        }
        binding.viewpager.isUserInputEnabled = false
        binding.viewpager.offscreenPageLimit = 3
        EMUtils().addFriend("17354704125","asdws")
    }

    override fun initListener() {
        binding.tvHome.setOnClickListener {

            setBottomIcon()
            BaseUtility.chanegeDrawableTop(context, R.mipmap.nav_icon_home_select,binding.tvHome)
            binding.viewpager.setCurrentItem(0,false)
        }
        binding.tvFriend.setOnClickListener {
            setBottomIcon()
            BaseUtility.chanegeDrawableTop(context,R.mipmap.nav_icon_friend_select,binding.tvFriend)
            binding.viewpager.setCurrentItem(1,false)
        }
        binding.tvMessage.setOnClickListener {
            setBottomIcon()
            BaseUtility.chanegeDrawableTop(context,R.mipmap.nav_icon_message_select,binding.tvMessage)
            binding.viewpager.setCurrentItem(2,false)
        }
        binding.tvMy.setOnClickListener {
            setBottomIcon()
            BaseUtility.chanegeDrawableTop(context,R.mipmap.nav_icon_usercenter_select,binding.tvMy)
            binding.viewpager.setCurrentItem(3,false)
        }

    }

    private fun setBottomIcon() {
        BaseUtility.chanegeDrawableTop(context,R.mipmap.nav_icon_home_normal,binding.tvHome)
        BaseUtility.chanegeDrawableTop(context,R.mipmap.nav_icon_friend_normal,binding.tvFriend)
        BaseUtility.chanegeDrawableTop(context,R.mipmap.nav_icon_message_normal,binding.tvMessage)
        BaseUtility.chanegeDrawableTop(context,R.mipmap.nav_icon_usercenter_normal,binding.tvMy)
    }

    override fun initViewModel() {

    }

    override fun initRequest() {

    }

    override fun initData() {

    }

}