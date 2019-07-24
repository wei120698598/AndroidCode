package com.wei.sample.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.properties.ObservableProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

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


interface Base {
    fun print()
}

class BaseImpl(val x: Int) : Base {
    override fun print() {
        print(x)
    }
}

class Derived(b: Base, c: Base) : Base by b {
    fun test() {
        println(".....")
    }
}


fun main() {
//    val e = Example()
//    println(e.p)     // 访问该属性，调用 getValue() 函数
//
//    e.p = "Runoob"   // 调用 setValue() 函数
//    println(e.p)

//    Derived(BaseImpl(10)).test()
}


