package com.wei.sample.clazz

import java.util.concurrent.Executors


/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description
 * @date 2019-05-16
 * @email weishuxin@icourt.cc
 */
class TestTryCatch1 {
    private var feild = "this is class variables"
    @Volatile
    var volatileFeild = "this is class volatile variables"

    init {
        println("from the class  block")
    }

    constructor() {

    }

    constructor(feild: String) {
        this.feild = feild
    }

    @Synchronized
    fun syncMethod() {
        println("from sync method ")
    }

    fun syncBlock() {
        synchronized(this) {
            println("from sync block")
        }
    }

    fun main(args: Array<String>): String {
        val executorService = Executors.newSingleThreadExecutor()
        executorService.submit { }


        try {
            staticMethod()
            syncBlock()
            return "from try"
        } catch (e: Exception) {
            e.printStackTrace()
            return "from catch"
        } finally {
            return "from finally"
        }
    }

    companion object {
        var staticFeild = "this is class static variables"
        val staticFinalFeild = "this is class static final variables"

        init {
            println("from the class static block")
        }

        fun staticMethod(): String {
            var a = 1
            val b = 10
            a = b
            return "from static method"
        }
    }
}
