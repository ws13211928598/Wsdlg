package com.example.wsdlg.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.wsdlg.MainActivity

/**
 *  created by ws
 *   on 2021/6/2
 *   describe:
 */
abstract class BaseFragment<VB:ViewBinding> : Fragment() {
    private lateinit var _binding: VB
    protected val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getBinding(inflater,container)
        return _binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initListener()
        initViewModel()
        initRequest()
        initData()
    }
      /*  bindingBase = getBinding(inflater,container)
        return bindingBase.root*/

    abstract fun getBinding(inflater: LayoutInflater, viewGroup: ViewGroup?): VB
    abstract fun initView()
    abstract fun initListener()
    abstract fun initViewModel()
    abstract fun initRequest()
    abstract fun initData()

    override fun onPause() {
        super.onPause()
    }


    //abstract fun getBinding(inflater: LayoutInflater, viewGroup: ViewGroup?): VB
}