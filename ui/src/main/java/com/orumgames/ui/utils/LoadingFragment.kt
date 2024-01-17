package com.orumgames.ui.utils

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.orumgames.ui.R

class LoadingFragment internal constructor(
    private val activity: Activity
) {
    private var dialog: AlertDialog? = null
    fun startLoadingDialog() {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.fragment_loading, null))
        builder.setCancelable(false)
        dialog = builder.create()
        dialog?.show()
    }

    fun dismissDialog() {
        dialog?.dismiss()
    }
}