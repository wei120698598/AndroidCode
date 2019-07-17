package com.wei.sample.coroutines

import com.bumptech.glide.load.engine.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.produce
import org.jetbrains.anko.coroutines.experimental.asReference
import org.jetbrains.annotations.TestOnly
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlin.properties.Delegates
import kotlin.properties.ObservableProperty
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import kotlin.properties.ReadWriteProperty as ReadWriteProperty1

@ExperimentalCoroutinesApi
fun main() {
    val start = GlobalScope.launch(Dispatchers.IO) {
        // 在后台启动一个新的协程并继续
        println("World!") // 在延迟后打印输出
    }.start()
    println("Hello,$start") // 协程已在等待时主线程还在继续


}


// 定义包含属性委托的类
class Example {
    var p: String by observable("") { prop, old, new ->
        println("旧值：$old -> 新值：$new")
    }
}

public inline fun <T> observable(initialValue: T, crossinline onChange: (property: KProperty<*>, oldValue: T, newValue: T) -> Unit):
        ReadWriteProperty<Any?, T> = object : ObservableProperty<T>(initialValue) {
    override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) = onChange(property, oldValue, newValue)

    override fun getValue(thisRef: Any?, property: KProperty<*>): T = initialValue

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        super.setValue(thisRef, property, value)
    }
}


fun main(args: Array<String>) {
    val e = Example()
    println(e.p)     // 访问该属性，调用 getValue() 函数

    e.p = "Runoob"   // 调用 setValue() 函数
    println(e.p)
}