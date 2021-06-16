package com.example.wsdlg.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.wsdlg.MainActivity


import com.example.wsdlg.utils.delegateutil.viewbinding
import gongren.com.dlg.databinding.ActivitySplashBinding
import kotlinx.coroutines.*
import java.util.*

class SplashActivity : AppCompatActivity() {
    private val binding by viewbinding<ActivitySplashBinding>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.textView.setText("")
        //setContentView(R.layout.activity_splash)
        init()
    }

    private fun init() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        GlobalScope.launch {
            //delay(3000)
            runOnUiThread {
                startActivity(Intent(this@SplashActivity,MainActivity::class.java))
                finish()
            }
        }
    }
}