package com.elnaz.theguardiannewsfeed.core

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.elnaz.theguardiannewsfeed.interfaces.BaseViewInterface

abstract class BaseActivity<T : ViewDataBinding>(@LayoutRes private val layoutRes: Int) :
    AppCompatActivity(), BaseViewInterface {

    protected lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!::binding.isInitialized)
            binding = DataBindingUtil.setContentView(this, layoutRes)
        setupViews()
    }

}