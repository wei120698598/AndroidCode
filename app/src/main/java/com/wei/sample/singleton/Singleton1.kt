package com.wei.sample.singleton

import java.util.*
import kotlin.collections.HashSet

/**
 * 懒汉式，线程不安全，仿Java
 */
internal class Singleton6 private constructor() {
    companion object {
        private var instance: Singleton6? = null

        fun getInstance(): Singleton6 {
            if (instance == null) {
                instance = Singleton6()
            }
            return instance!!
        }
    }
}

/**
 * 懒汉式，线程不安全
 */
internal class Singleton7 private constructor() {
    companion object {
        var instance: Singleton7? = null
            get() {
                if (field == null) {
                    field = Singleton7()
                }
                return field
            }

    }
}

/**
 * 懒汉式，线程安全
 */
internal class Singleton8 private constructor() {
    companion object {
        @get:Synchronized
        var instance: Singleton8? = null
            get() {
                if (field == null) {
                    field = Singleton8()
                }
                return field
            }
    }
}

/**
 *饿汉式
 */
internal object Singleton9 {
    fun whateverMethod() {
        println("....")
    }
}

/**
 *饿汉式，仿Java
 */
internal class Singleton10 private constructor() {
    companion object {
        val instance = Singleton10()
    }
}

/**
 * 双检锁/双重校验锁（DCL，即 double-checked locking）
 */
internal class Singleton12 private constructor() {
    companion object {
        val instance: Singleton12 by lazy {
            Singleton12()
        }
    }
}

/**
 * 双检锁/双重校验锁（DCL，即 double-checked locking）,仿Java
 */
internal class Singleton11 private constructor() {
    companion object {
        @Volatile
        var instance: Singleton11? = null
            get() {
                if (field == null) {
                    synchronized(Singleton11::class) {
                        if (field == null) {
                            field = Singleton11()
                        }
                    }
                }
                return field
            }
    }
}

/**
 * 登记式/静态内部类
 */
internal class Singleton13 {
    private object SingletonHolder {
        val INSTANCE = Singleton13()
    }

    companion object {
        val instance: Singleton13
            get() = SingletonHolder.INSTANCE
    }
}

/**
 * 枚举
 */
internal enum class Singleton14 {
    INSTANCE;

    fun whateverMethod() {
        println("....")
    }
}

fun main() {

    val synchronizedSet = Collections.synchronizedSet(HashSet<Singleton13>())
    for (a in 1..10) {
        Thread {
            synchronizedSet.add(Singleton13.instance)
        }.start()
        Singleton14.INSTANCE.whateverMethod()
    }

    println(synchronizedSet.size)
}