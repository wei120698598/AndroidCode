package com.wei.component.platform.view.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.wei.component.platform.view.base.viewmodel.BaseAdapterItemViewModel
import java.lang.reflect.ParameterizedType

abstract class BaseBindingAadapter<VDB : ViewDataBinding, Model, VM : BaseAdapterItemViewModel<Model>>(dataList: List<Model>?) :
    RecyclerView.Adapter<BaseBindingViewHolder<VDB, Model, VM>>() {

    init {
        dataList?.let {
            this.dataList.removeOnListChangedCallback(dataListChangeCallback)
            this.dataList.addOnListChangedCallback(dataListChangeCallback)
            this.dataList.addAll(dataList)
        }
    }

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BaseBindingViewHolder(createBinding(parent, viewType), createViewModel())

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holderBinding: BaseBindingViewHolder<VDB, Model, VM>, position: Int) {
        bindViewHolder(holderBinding, dataList[position], position)
    }

    abstract fun createViewModel(): VM
    abstract fun bindViewHolder(holderBinding: BaseBindingViewHolder<VDB, Model, VM>, model: Model, position: Int)

    private fun createBinding(viewGroup: ViewGroup, viewType: Int): VDB {
        val bindingClass =
            (this::class.java.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VDB>
        val method = bindingClass.getMethod(
            "inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.javaPrimitiveType
        )
        return method.invoke(null, LayoutInflater.from(viewGroup.context), viewGroup, false) as VDB
    }

    open fun bindData(dataList: List<Model>, isRefresh: Boolean = true) {
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

    open fun addData(index: Int = this.dataList.size - 1, item: Model?, dataList: List<Model>?) {
        item?.let {
            this.dataList.add(index, item)
        }
    }

    open fun removeData(item: Model?, dataList: List<Model>?) {

    }

    fun clear() {
        dataList.clear()
    }
}