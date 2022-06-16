package com.elnaz.theguardiannewsfeed.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.elnaz.theguardiannewsfeed.interfaces.BaseCallBackInterface
import com.elnaz.theguardiannewsfeed.interfaces.BaseViewInterface

abstract class BaseFragment<T : ViewDataBinding>(@LayoutRes private val layoutRes: Int) :
    Fragment(), BaseViewInterface {

    protected lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!::binding.isInitialized) {
            binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
            setupViews()
        }
        return binding.root as ViewGroup
    }

    override fun onDestroyView() {
        if (::binding.isInitialized)
            binding.unbind()
        super.onDestroyView()
    }

}