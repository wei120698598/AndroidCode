package com.wei.component.platform.view.base.bind

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.wei.component.platform.view.base.viewmodel.BaseViewModel

/**
 * v1.0 of the file created on 2019-07-31 by shuxin.wei, email: weishuxin@maoyan.com
 * description: 创建[BaseViewModel]和[ViewDataBinding]
 */
interface BindFactory {
    /**
     * 使用[clazz]来生成[BaseViewModel]
     */
    fun <VM : BaseViewModel> bindVM(clazz: Class<VM>): VM

    /**
     * 使用[layoutResId]来生成[ViewDataBinding]
     */
    fun <VDB : ViewDataBinding> bindView(@LayoutRes layoutResId: Int): VDB

    /**
     * 使用[clazz]来生成[ViewDataBinding]
     */
    fun <VDB : ViewDataBinding> bindView(clazz: Class<VDB>): VDB

    /**
     * 可以覆写此方法，绑定[BaseViewModel]到[ViewDataBinding]
     */
    fun bindViewModelToView()
}
