@file:Suppress("UNCHECKED_CAST")
@file:JvmName("BindProcessor")

package com.zanfou.component.platform.view.base.bind

import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.zanfou.component.platform.view.base.viewmodel.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 * v1.0 of the file created on 2019-08-01 by shuxin.wei, email: weishuxin@maoyan.com
 * description: ViewDataBinding和ViewModel工具类
 */

/**
 * 在[FragmentActivity] 创建 [BaseViewModel]
 * 会优先检查[BindVM]注解方式，如果没有使用注解，则根据泛型来创建
 */
fun <VM : BaseViewModel> FragmentActivity.processBindVM(): VM? {
    val clazz = this::class.java

    clazz.getAnnotation(BindVM::class.java)?.let {
        return ViewModelProviders.of(this).get(it.value::class.java as Class<VM>)
    }

    (clazz.genericSuperclass as? ParameterizedType)?.actualTypeArguments?.let {
        if (it.isNotEmpty()) {
            return ViewModelProviders.of(this).get(it[1] as Class<VM>)
        }
    }
    return null
}

/**
 * 在[Fragment] 创建 [BaseViewModel]
 * 会优先检查[BindVM]注解方式，如果没有使用注解，则根据泛型来创建
 */
fun <VM : BaseViewModel> Fragment.processBindVM(): VM? {
    val clazz = this::class.java
    clazz.getAnnotation(BindVM::class.java)?.let {
        return ViewModelProviders.of(this).get(it.value::class.java as Class<VM>)
    }

    (clazz.genericSuperclass as? ParameterizedType)?.actualTypeArguments?.let {
        if (it.isNotEmpty()) {
            return ViewModelProviders.of(this).get(it[1] as Class<VM>)
        }
    }
    return null
}


/**
 * 创建 [BaseViewModel]
 * 会优先检查[BindView]注解方式，如果没有使用注解，则根据泛型来创建;
 * 如果使用注解方式，由于在lib中[R]文件中的layoutId不是静态常量，所以可以使用[ViewDataBinding.javaClass]的方式；
 * 创建优先级 layoutId > [ViewDataBinding.javaClass] > generic
 */
fun <VDB : ViewDataBinding> createViewDataBinding(inflater: LayoutInflater, clazz: Class<*>): VDB? {
    try {
        clazz.getAnnotation(BindView::class.java)?.let {
            val layoutResId = it.value
            return if (layoutResId != -1) {
                createViewDataBinding(inflater, layoutResId)
            } else {
                it.viewDataBinding::class.java.getMethod("inflate", LayoutInflater::class.java).invoke(
                    null,
                    inflater
                ) as VDB
            }
        }


        val genericSuperclass = clazz.genericSuperclass as ParameterizedType
        val actualTypeArguments = genericSuperclass.actualTypeArguments
        if (!actualTypeArguments.isNullOrEmpty()) {
            return (actualTypeArguments[0] as Class<VDB>).getMethod("inflate", LayoutInflater::class.java).invoke(
                null,
                inflater
            ) as VDB
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return null
}

/**
 * 利用[DataBindingUtil]通过[LayoutRes]来创建[ViewDataBinding]
 */
fun <VDB : ViewDataBinding> createViewDataBinding(inflater: LayoutInflater, @LayoutRes layoutResId: Int): VDB =
    DataBindingUtil.inflate(inflater, layoutResId, null, false)

/**
 * 利用[ViewDataBinding.javaClass]通过反射来创建[ViewDataBinding]
 */
fun <VDB : ViewDataBinding> Class<VDB>.createViewDataBinding(inflater: LayoutInflater): VDB =
    getMethod("inflate", LayoutInflater::class.java).invoke(null, inflater) as VDB

/**
 * 利用[BaseViewModel.javaClass]在[FragmentActivity]中创建[BaseViewModel]实例
 */
fun <VM : BaseViewModel> Class<VM>.createViewModel(activity: FragmentActivity) =
    ViewModelProviders.of(activity).get(this)

/**
 * 利用[BaseViewModel.javaClass]在[Fragment]中创建[BaseViewModel]实例
 */
fun <VM : BaseViewModel> Class<VM>.createViewModel(fragment: Fragment) =
    ViewModelProviders.of(fragment).get(this)

/**
 * 获取特定[Class]的第[index]个泛型对应的[Class]
 */
fun <T> Class<*>.getGenericActualTypeArguments(index: Int) =
    (genericSuperclass as ParameterizedType).actualTypeArguments[index] as Class<T>

