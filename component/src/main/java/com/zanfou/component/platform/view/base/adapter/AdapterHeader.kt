package com.zanfou.component.platform.view.base.adapter

import android.view.View
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

interface AdapterHeader {
    @IntRange(from = 0)
    fun getHeaderCount(): Int

    fun addHeader(view: View)

    fun addHeader(@LayoutRes layoutResId: Int, recyclerView: RecyclerView)

    fun removeHeader(@IntRange(from = 0) indexOfHeader: Int)

    fun removeHeader(view: View)

    fun removeAllHeader()

    fun getHeader(@IntRange(from = 0) indexOfHeader: Int): View
}