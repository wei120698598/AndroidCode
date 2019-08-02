package com.zanfou.component.platform.view.base.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.zanfou.component.platform.view.base.bind.BindFactory
import com.zanfou.component.platform.view.base.bind.createViewDataBinding
import com.zanfou.component.platform.view.base.bind.createViewModel
import com.zanfou.component.platform.view.base.viewmodel.BaseViewModel

/**
 * v1.0 of the file created on 2019-07-31 by shuxin.wei, email: weishuxin@maoyan.com
 * description: 如果使用MVVM，需要指定[ViewDataBinding]和[BaseViewModel]泛型，会根据泛型来创建
 */
abstract class BaseActivity : AppCompatActivity(), BindFactory {

    override fun <VM : BaseViewModel> bindVM(clazz: Class<VM>): VM = clazz.createViewModel(this)

    override fun <VDB : ViewDataBinding> bindView(layoutResId: Int): VDB =
        createViewDataBinding(layoutInflater, layoutResId)

    override fun <VDB : ViewDataBinding> bindView(clazz: Class<VDB>): VDB = clazz.createViewDataBinding(layoutInflater)
}