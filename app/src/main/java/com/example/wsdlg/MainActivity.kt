package com.example.wsdlg

import android.Manifest
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.View
import android.view.animation.Animation
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.blankj.utilcode.util.SPUtils
import com.bumptech.glide.Glide
import com.example.wsdlg.base.BaseActivity


import com.example.wsdlg.utils.delegateutil.viewbinding
import com.example.wsdlg.view_model.MainViewModel
import com.github.dfqin.grantor.PermissionListener
import com.github.dfqin.grantor.PermissionsUtil
import gongren.com.dlg.R
import gongren.com.dlg.databinding.ActivityMainBinding


class MainActivity : BaseActivity() {
    companion object {
        var mainViewModel: MainViewModel = MainViewModel()
        var navController: NavController? = null
        var navHostFragment: NavHostFragment? = null
        var iv_load: ImageView? = null
        var con_load: ConstraintLayout? = null
        var animator: ObjectAnimator? = null
        fun getCurrentFragment(): Fragment {
            return navHostFragment?.childFragmentManager!!.primaryNavigationFragment!!
        }

        @SuppressLint("ObjectAnimatorBinding")
        fun startAnimation() {
            con_load?.visibility =View.VISIBLE
            animator = ObjectAnimator.ofFloat(iv_load, "translationY", 0f, -100f, 0f)
            animator?.duration = 3000
            animator?.repeatCount = Animation.INFINITE
            animator?.start()

        }

        fun stopAnimation() {
            con_load?.visibility =View.GONE
            animator?.cancel()
        }
    }

    private val binding by viewbinding<ActivityMainBinding>()
    override fun initViewModel() {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.binding.setText("")
        val token: String = SPUtils.getInstance().getString("token")
        if (token.isNotEmpty()) {
            mainViewModel!!.mediatorLiveData.value!!.login = true
        }
    }

    override fun initView() {

        iv_load = binding.ivYasina
        con_load = binding.consLoad
        Glide.with(this).load(R.mipmap.yasina).into(iv_load!!)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_main) as NavHostFragment
        navController = navHostFragment!!.navController
        initLogin()
        //startAnimation()
        //stopAnimation()
        //navController.navigate(R.id.action_mainFragment_to_registerFragment)
    }

    private fun initLogin() {

    }


    override fun initListener() {
        con_load?.setOnClickListener {

        }

    }

    override fun initRequest() {
        requestPermission()
        /*val hashMap = HashMap<String, Any>()
        hashMap.put("phoneNumber",13211928598)
        mainViewModel?.request("send_code",hashMap)*/
    }

    private fun requestPermission() {
        val deniedPermissions = arrayOf<String>(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.CAMERA
        )
        PermissionsUtil.requestPermission(this, object : PermissionListener {
            override fun permissionGranted(permission: Array<out String>) {

            }

            override fun permissionDenied(permission: Array<out String>) {

            }
        }, deniedPermissions, false, null)
    }

    override fun initData() {
        val userId = SPUtils.getInstance().getInt("userId")
        mainViewModel.mediatorLiveData.value!!.userId = userId

    }


}