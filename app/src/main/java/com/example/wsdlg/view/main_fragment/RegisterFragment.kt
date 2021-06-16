package com.example.wsdlg.view.main_fragment


import android.annotation.SuppressLint
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.lib_network.bean.SendCodeBean
import com.example.wsdlg.MainActivity
import com.example.wsdlg.base.BaseFragment
import com.example.wsdlg.utils.PhoneFormatCheckUtils
import com.example.wsdlg.utils.toast
import gongren.com.dlg.R
import gongren.com.dlg.databinding.FragmentRegisterBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    var time = 60
    override fun getBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup?
    ): FragmentRegisterBinding {
        return FragmentRegisterBinding.inflate(layoutInflater, viewGroup, false)
    }

    @SuppressLint("ResourceAsColor")
    override fun initView() {
        initPact()
        Log.d("asdws", "initViewModel: 2")
        binding.edPhone.setText(MainActivity.mainViewModel!!.mediatorLiveData.value!!.phoneNumber)
    }

    private fun initPact() {
        val spannableString = SpannableString("登录即表示同意《用户协议》及《隐私政策》")
        spannableString.setSpan(object : ClickableSpan() {
            @SuppressLint("ResourceAsColor")
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false//去掉下划线
                ds.color = Color.parseColor("#ff7800") //设置文字颜色

            }

            override fun onClick(widget: View) {

            }

        }, 7, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(object : ClickableSpan() {
            @SuppressLint("ResourceAsColor")
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false//去掉下划线
                ds.color = Color.parseColor("#ff7800") //设置文字颜色

            }

            override fun onClick(widget: View) {

            }

        }, 14, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvPact.movementMethod = LinkMovementMethod.getInstance()
        binding.tvPact.text = spannableString
    }

    override fun initListener() {
        binding.btnSendCode.setOnClickListener {
            //验证手机号
            if (!PhoneFormatCheckUtils.isPhoneLegal(binding.edPhone.text.toString())) {
                "请检查手机号码".toast()
                return@setOnClickListener
            }
            if (MainActivity.mainViewModel!!.mediatorLiveData.value!!.clickOn) {
                val hashMap = HashMap<String, Any>()
                hashMap.put("phoneNumber", binding.edPhone.text)
                MainActivity.mainViewModel!!.mediatorLiveData.value!!.phoneNumber =
                    binding.edPhone.text.toString()
                MainActivity.mainViewModel!!.request("send_code", hashMap)
            }
        }

    }

    override fun initViewModel() {

        MainActivity.mainViewModel?.successLiveData?.observe(this, Observer<Any> {
            //判断是否显示
            if (MainActivity.getCurrentFragment() is RegisterFragment) {
                when (it) {
                    is SendCodeBean -> {
                        Log.d("asdws", "initViewModel: 1234")
                        MainActivity.navController?.navigate(R.id.action_registerFragment_to_logInFragment)
                        GlobalScope.launch {
                            var nowTime = 0

                            while (nowTime < time) {
                                delay(1000)
                                nowTime++
                                activity?.runOnUiThread {
                                    binding.btnSendCode.isEnabled = false
                                    binding.btnSendCode.text = (time - nowTime).toString()
                                }
                            }
                            activity?.runOnUiThread {
                                binding.btnSendCode.isEnabled = true
                                binding.btnSendCode.text = "获取验证码"
                            }
                        }
                    }
                }
            }


        }
        )

    }

    override fun initRequest() {

    }

    override fun initData() {

    }

}