package com.tdi.holyquran.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import com.tdi.holyquran.di.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import java.util.concurrent.TimeUnit
import javax.inject.Inject

abstract class BaseFragment<T : ViewDataBinding, VM : ViewModel> : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelProviderFactory

    lateinit var mViewBinding: T
    lateinit var mViewModel: VM

    abstract var getLayoutId: Int
    abstract var getViewModel: Class<VM>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewBinding = DataBindingUtil.inflate(inflater, getLayoutId, container, false)
        mViewModel = ViewModelProvider(this, factory)[getViewModel]

        return mViewBinding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
////
//
//    }
}