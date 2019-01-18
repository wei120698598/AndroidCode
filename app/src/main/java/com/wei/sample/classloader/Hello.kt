package com.wei.sample.classloader

import android.os.Message

import java.util.logging.Handler

/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description 先说一下Java程序初始化的顺序:父类静态变量>父类静态代码块>子类静态变量>子类静态代码块>父类非静态变量>父类非静态代码块>父类构造器>子类非静态变量>子类非静态代码块>子类构造器。
 *
 *
 * Java程序初始化一般遵循3个原则
 * 1.静态对象（变量）先于非静态对象（变量）初始化。其中静态对象（变量）只初始化一次，因为static在jvm中只有一块区域存储，方法区（Method Area），
 * 他之所以被称为静态是因为从程序创建到死亡他的地址值都不会改变，他只在class类对象初次加载时初始化，因此static只需要初始化一次，而非静态对象（变量）可能会初始化很多次。
 *
 *
 * 2.如果类之间存在继承关系，那么父类优先于子类进行初始化。
 *
 *
 * 3.按照成员变量的定义顺序进行初始化。即使变量定义散布于方法之中，他们依然在任何方法（包括构造函数）被调用前先初始化
 * @date 2018/12/18
 * @email weishuxin@icourt.cc
 */
internal open class ClassLoaderA {

    init {
        print("3")
    }

    init {
        print("2")
    }

    open fun print() {
        print("4")
    }

    companion object {
        init {
            print("1")
        }
    }
}

internal class ClassLoaderB : ClassLoaderA() {

    init {
        print("c")
    }

    init {
        print("b")
    }

    override fun print() {
        print("d")
    }

    companion object {
        init {
            print("a")
        }
    }
}

object Hello {
    @JvmStatic
    fun main(args: Array<String>) {
        var classLoaderB: ClassLoaderA = ClassLoaderB()
        classLoaderB.print()
        classLoaderB = ClassLoaderB()

        object : android.os.Handler(Callback { false }) {
            override fun handleMessage(msg: Message) {}
        }.sendEmptyMessage(0)
    }
}