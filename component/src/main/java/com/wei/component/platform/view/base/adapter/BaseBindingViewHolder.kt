package com.wei.component.platform.view.base.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.wei.component.platform.view.base.viewmodel.BaseAdapterItemViewModel

class BaseBindingViewHolder<VDB : ViewDataBinding, Model, VM : BaseAdapterItemViewModel<Model>>(
    binding: VDB,
    viewModel: VM
) : RecyclerView.ViewHolder(binding.root)