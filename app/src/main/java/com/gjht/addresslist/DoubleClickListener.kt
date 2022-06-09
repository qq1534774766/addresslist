package com.gjht.addresslist

import android.view.View

abstract class DoubleClickListener : View.OnClickListener {
    override fun onClick(v: View) {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastClickTime < DOUBLE_TIME) {
            onDoubleClick(v)
        }
        lastClickTime = currentTimeMillis
    }

    abstract fun onDoubleClick(v: View?)

    companion object {
        private const val DOUBLE_TIME: Long = 1000
        private var lastClickTime: Long = 0
    }
}