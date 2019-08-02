package com.zanfou.component.platform.view.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.zanfou.component.platform.view.base.bind.BindFactory
import com.zanfou.component.platform.view.base.bind.getGenericActualTypeArguments
import com.zanfou.component.platform.view.base.viewmodel.BaseViewModel

/**
 * v1.0 of the file created on 2019-07-31 by shuxin.wei, email: weishuxin@maoyan.com
 * description: 如果使用MVVM，需要指定[ViewDataBinding]和[BaseViewModel]泛型，会根据泛型来创建
 */
abstract class BaseBindingFragment<VDB : ViewDataBinding, VM : BaseViewModel> : BaseFragment(), BindFactory {

    var binding: VDB? = null
    var vm: VM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindView(clazz = this::class.java.getGenericActualTypeArguments(0))
        vm = bindVM(this::class.java.getGenericActualTypeArguments(1))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        binding?.root?.also {
            (it.parent as? ViewGroup)?.removeView(it)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModelToView()
    }

    override fun bindViewModelToView() {

    }

    override fun onDestroy() {
        super.onDestroy()
        binding?.unbind()
    }
}