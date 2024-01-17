package com.orumgames.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VBinding: ViewBinding, VModel: BaseViewModel> : Fragment() {

    protected lateinit var mViewBinding: VBinding
    protected abstract val mViewModel: VModel
    protected abstract fun getViewBinding(): VBinding
    protected val mTag: String = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupViewModel()
        attachObservers()
        attachListeners()
    }

    private fun init() {
        mViewBinding = getViewBinding()
    }

    open fun setupViews() {}
    open fun setupViewModel() {}
    open fun attachObservers() {}
    open fun attachListeners() {}
}