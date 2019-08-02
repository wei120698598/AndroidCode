package com.zanfou.component.platform.view.base.adapter

import android.view.View
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

interface AdapterFooter {
    @IntRange(from = 0)
    fun getFooterCount(): Int

    fun addFooter(view: View)

    fun addFooter(@LayoutRes layoutResId: Int, recyclerView: RecyclerView)

    fun removeFooter(@IntRange(from = 0) indexOfFooter: Int)

    fun removeFooter(view: View)

    fun removeAllFooter()

    fun getFooter(@IntRange(from = 0) indexOfFooter: Int): View
}