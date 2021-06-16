import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.SPUtils
import com.example.lib_network.bean.SendCodeBean
import com.example.lib_network.bean.UserLoginMode
import com.example.wsdlg.MainActivity
import com.example.wsdlg.base.BaseFragment
import gongren.com.dlg.R
import gongren.com.dlg.databinding.FragmentLogInBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class LogInFragment :BaseFragment<FragmentLogInBinding>(){
    var time = 60
    override fun getBinding(inflater: LayoutInflater, viewGroup: ViewGroup?): FragmentLogInBinding {
        return FragmentLogInBinding.inflate(layoutInflater,viewGroup,false)
    }

    override fun initView() {
        Log.d("asdws", "initView: ")
        binding.pwdEditText.setOnTextChangeListener {
            if (it.length == binding.pwdEditText.textLength){
                val hashMap = HashMap<String, Any>()
                hashMap["phoneNumber"] = MainActivity.mainViewModel.mediatorLiveData.value!!.phoneNumber
                hashMap["oneTimeVCode"] = it
                Log.d("asdws", "initView: ")
                MainActivity.mainViewModel.request("verify_code",hashMap)
            }
        }

    }

    override fun initListener() {
        binding.tvAgainCode.setOnClickListener {
            val hashMap = HashMap<String, Any>()
            hashMap.put("phoneNumber", MainActivity.mainViewModel!!.mediatorLiveData.value!!.phoneNumber)
            MainActivity.mainViewModel!!.request("send_code",hashMap)
        }
    }

    override fun initViewModel() {
        Log.d("asdws", "initViewModel: ")
        MainActivity.mainViewModel.successLiveData.observe(this, Observer<Any> {
            if (MainActivity.getCurrentFragment() is LogInFragment) {
                when (it) {
                    is SendCodeBean -> {
                        GlobalScope.launch {
                            var nowTime = 0

                            while (nowTime < time) {
                                delay(1000)
                                nowTime++
                                activity?.runOnUiThread {
                                    Log.d("asdws", "initViewModel: " + nowTime)
                                    binding.tvAgainCode.isEnabled = false
                                    binding.tvAgainCode.text = (time - nowTime).toString()
                                }
                            }
                            activity?.runOnUiThread {
                                binding.tvAgainCode.isEnabled = true
                                binding.tvAgainCode.text = "重新获取验证码"
                            }
                        }
                    }
                    is UserLoginMode -> {
                        Log.d("asdws", "initViewModel: 53")
                        SPUtils.getInstance().put("userId", it.userId)
                        SPUtils.getInstance().put("token", it.token)
                        MainActivity.mainViewModel!!.mediatorLiveData.value!!.login = true
                        MainActivity.navController?.navigate(R.id.action_logInFragment_to_mainFragment)
                    }
                }
            }
        })
    }



    override fun initRequest() {

    }

    override fun initData() {

    }

}