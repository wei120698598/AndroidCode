package com.zanfou.component.platform.view.base.bind

import com.zanfou.component.platform.view.base.viewmodel.BaseViewModel
import java.lang.annotation.Inherited
import kotlin.reflect.KClass

/**
 * v1.0 of the file created on 2019-07-31 by shuxin.wei, email: weishuxin@maoyan.com
 * description: 利用注解绑定VM
 */
@Deprecated("已利用泛型或者直接调用来实现，暂时使用不上，不建议使用")
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Inherited
annotation class BindVM(
    val value: KClass<out BaseViewModel>
)