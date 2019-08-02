package com.zanfou.component.platform.view.base.adapter

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import androidx.core.util.isNotEmpty
import androidx.core.util.size
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView

abstract  class BaseAadapter<Model>(dataList: List<Model>?) : RecyclerView.Adapter<BaseViewHolder>(),
    AdapterFooter,
    AdapterHeader {

    private val HEADER_VIEW_TYPE = -1
    private val FOOTER_VIEW_TYPE = -2
    private val headerSparseArray = SparseArray<View>()
    private val footerSparseArray = SparseArray<View>()


    private val dataList: ObservableArrayList<Model> by lazy {
        ObservableArrayList<Model>()
    }
    private val dataListChangeCallback: ObservableList.OnListChangedCallback<ObservableArrayList<Model>> by lazy {
        object : ObservableList.OnListChangedCallback<ObservableArrayList<Model>>() {
            override fun onChanged(sender: ObservableArrayList<Model>?) {
                notifyDataSetChanged()
            }

            override fun onItemRangeRemoved(sender: ObservableArrayList<Model>?, positionStart: Int, itemCount: Int) {
                notifyItemRangeRemoved(positionStart, itemCount)
            }

            override fun onItemRangeMoved(
                sender: ObservableArrayList<Model>?,
                fromPosition: Int,
                toPosition: Int,
                itemCount: Int
            ) {
                notifyItemMoved(fromPosition, toPosition)
            }

            override fun onItemRangeInserted(sender: ObservableArrayList<Model>?, positionStart: Int, itemCount: Int) {
                notifyItemRangeInserted(positionStart, itemCount)
            }

            override fun onItemRangeChanged(sender: ObservableArrayList<Model>?, positionStart: Int, itemCount: Int) {
                notifyItemRangeRemoved(positionStart, itemCount)
            }

        }
    }


    override fun getFooterCount(): Int = footerSparseArray.size

    override fun addFooter(view: View) {
        footerSparseArray.put(footerSparseArray.size, view)
    }

    override fun addFooter(layoutResId: Int, recyclerView: RecyclerView) {
        footerSparseArray.put(
            footerSparseArray.size,
            LayoutInflater.from(recyclerView.context).inflate(layoutResId, recyclerView, false)
        )
    }

    override fun removeFooter(indexOfFooter: Int) {
        if (indexOfFooter in 0 until footerSparseArray.size) {
            footerSparseArray.removeAt(indexOfFooter)
            notifyItemRemoved(getHeaderCount() + getDataCount() + indexOfFooter)
        }
    }

    override fun removeFooter(view: View) {
        removeFooter(footerSparseArray.indexOfValue(view))
    }

    override fun removeAllFooter() {
        if (footerSparseArray.isNotEmpty()) {
            footerSparseArray.clear()
            notifyItemRangeRemoved(getHeaderCount() + getDataCount(), footerSparseArray.size)
        }
    }

    override fun getFooter(indexOfFooter: Int): View = footerSparseArray.valueAt(indexOfFooter)

    override fun getHeaderCount(): Int = headerSparseArray.size

    override fun addHeader(view: View) {
        headerSparseArray.put(headerSparseArray.size, view)
    }

    override fun addHeader(layoutResId: Int, recyclerView: RecyclerView) {
        headerSparseArray.put(
            headerSparseArray.size,
            LayoutInflater.from(recyclerView.context).inflate(layoutResId, recyclerView, false)
        )
    }

    override fun removeHeader(indexOfHeader: Int) {
        if (indexOfHeader in 0 until headerSparseArray.size) {
            headerSparseArray.removeAt(indexOfHeader)
            notifyItemRemoved(indexOfHeader)
        }
    }

    override fun removeHeader(view: View) {
        removeHeader(headerSparseArray.indexOfValue(view))
    }

    override fun removeAllHeader() {
        if (headerSparseArray.isNotEmpty()) {
            headerSparseArray.clear()
            notifyItemRangeRemoved(0, headerSparseArray.size)
        }
    }

    override fun getHeader(indexOfHeader: Int): View = headerSparseArray.valueAt(indexOfHeader)


    init {
        dataList?.let {
            this.dataList.removeOnListChangedCallback(dataListChangeCallback)
            this.dataList.addOnListChangedCallback(dataListChangeCallback)
            this.dataList.addAll(dataList)
        }
    }


//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
//        return when (viewType) {
//            HEADER_VIEW_TYPE -> {
//                BaseViewHolder(headerSparseArray.get())
//            }
//            FOOTER_VIEW_TYPE -> {
//            }
//
//
//        }
//    }

//    fun createViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
//
//    }

    fun getDataCount() = dataList.size

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holderBinding: BaseViewHolder, position: Int) {
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }


    fun bindData(dataList: List<Model>, isRefresh: Boolean = true) {
        if (isRefresh) {
            clear()
            this.dataList.removeOnListChangedCallback(dataListChangeCallback)
            this.dataList.addAll(dataList)
            notifyDataSetChanged()
            this.dataList.addOnListChangedCallback(dataListChangeCallback)
        } else {
            clear()
            this.dataList.addAll(dataList)
        }
    }

    open fun addData(index: Int = this.dataList.size - 1, item: Model) {
        this.dataList.add(index, item)
    }

    open fun removeData(item: Model): Boolean {
        return this.dataList.remove(item)
    }

    fun clear() {
        dataList.clear()
    }
}