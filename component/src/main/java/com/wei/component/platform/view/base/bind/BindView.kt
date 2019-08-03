package com.wei.component.platform.view.base.bind

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import java.lang.annotation.Inherited
import kotlin.reflect.KClass

/**
 * v1.0 of the file created on 2019-07-31 by shuxin.wei, email: weishuxin@maoyan.com
 * description:
 * 在app中可以使用 value -> [LayoutRes]
 * 在 library 中 R 文件中的 id 不是 final 修饰，不能使用 value。可以使用 ViewDataBinding。
 * 注：与 kotlin 结合时需要指定 ViewDataBinding 全路径，否则在编译期无法找到生成的 ViewDataBinding
 */
@Deprecated("已利用泛型或者直接调用来实现，暂时使用不上，不建议使用")
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Inherited
annotation class BindView(
    @LayoutRes val value: Int = -1, val viewDataBinding: KClass<out ViewDataBinding> = ViewDataBinding::class
)