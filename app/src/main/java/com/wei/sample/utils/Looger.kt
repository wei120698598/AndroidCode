package com.wei.sample.utils

fun Any?.logI(tag: String = this?.javaClass?.name ?: "shuxin.wei") {
    Logger.logI(tag, this.toString())
}

fun Any?.logD(tag: String = this?.javaClass?.name ?: "shuxin.wei") {
    Logger.logD(tag, this.toString())
}

fun Any?.logE(tag: String = this?.javaClass?.name ?: "shuxin.wei") {
    Logger.logE(tag, this.toString())
}

fun Any?.logW(tag: String = this?.javaClass?.name ?: "shuxin.wei") {
    Logger.logE(tag, this.toString())
}