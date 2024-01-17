package com.orumgames.ui.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    protected val mTag: String = this::class.java.simpleName
}