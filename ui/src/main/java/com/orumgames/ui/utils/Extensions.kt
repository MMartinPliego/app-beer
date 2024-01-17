package com.orumgames.ui.utils

import android.app.AlertDialog
import android.view.View
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.bumptech.glide.Glide
import com.orumgames.ui.R
import com.orumgames.ui.callbacks.HomeCallbacks

var dialog: AlertDialog? = null

fun View.visible() {
    this.visibility = VISIBLE
}

fun ImageView.loadImage(url: Int) {
    Glide.with(this.context).load(url).into(this)
}

fun ImageView.loadImageUrl(url: String?) {
    Glide.with(this.context).load(url).into(this)
}

fun onBackPressed(fragment: Fragment, activity: FragmentActivity) {
    activity.onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController(fragment).popBackStack()
        }
    })
}

fun onBackPressedNavigateToHome(activity: FragmentActivity, listener: HomeCallbacks?, fragmentCallback: (() -> Unit)? = null) {
    activity.onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            fragmentCallback?.invoke()
            listener?.selectedHome()
        }
    })
}

fun <T> merge(first: List<T>?, second: List<T>?): List<T> {
    if (first == null && second == null) {
        return emptyList()
    }
    val list: MutableList<T> = ArrayList()
    list.addAll(first ?: emptyList())
    list.addAll(second ?: emptyList())
    return list
}

fun loadingFragment(activity: FragmentActivity) {
    val builder = AlertDialog.Builder(activity)
    val inflater = activity.layoutInflater
    builder.setView(inflater.inflate(R.layout.fragment_loading, null))
    builder.setCancelable(false)
    dialog = builder.create()
    dialog?.show()
}

fun dismissFragment() {
    dialog?.dismiss()
}