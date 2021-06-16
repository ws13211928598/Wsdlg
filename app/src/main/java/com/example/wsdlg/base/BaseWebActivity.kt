package com.example.wsdlg.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import gongren.com.dlg.R

class BaseWebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_web)
        initWeb()
    }

    private fun initWeb() {

    }
}