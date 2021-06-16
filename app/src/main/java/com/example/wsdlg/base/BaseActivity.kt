package com.example.wsdlg.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 *  created by ws
 *   on 2021/6/2
 *   describe:
 */
abstract class BaseActivity :AppCompatActivity(){
    var savedInstance: Bundle ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstance = savedInstanceState
        initBase()
        initViewModel()
        initView()
        initListener()
        initRequest()
        initData()
    }

    private fun initBase() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    abstract fun initViewModel()
    //abstract fun getViewBinding():VB
    abstract fun initView()
    abstract fun initListener()
    abstract fun initRequest()
    abstract fun initData()
}