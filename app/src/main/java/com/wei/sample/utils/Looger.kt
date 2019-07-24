package com.wei.sample.utils

import android.util.Log

internal const val DEBUG = true
internal const val TAG = "shuxin.wei"

fun Any?.logI(tag: String = TAG, debug: Boolean = true) {
    if (DEBUG && debug) {
        Log.i(tag, this.toString())
    }
}

fun Any?.logD(tag: String = TAG, debug: Boolean = true) {
    if (DEBUG && debug) {
        Log.d(tag, this.toString())
    }
}

fun Any?.logE(tag: String = TAG, debug: Boolean = true) {
    if (DEBUG && debug) {
        Log.e(tag, this.toString())
    }
}

fun Any?.logW(tag: String = TAG, debug: Boolean = true) {
    if (DEBUG && debug) {
        Log.w(tag, this.toString())
    }
}

fun Any?.logP(tag: String = TAG, debug: Boolean = true) {
    if (DEBUG && debug) {
        println("$tag : $this")
    }
}

fun printThread(tag: String = TAG) {
    "$tag: ThreadName:${Thread.currentThread().name} ThreadId: ${Thread.currentThread().id}".logD(tag)
}
