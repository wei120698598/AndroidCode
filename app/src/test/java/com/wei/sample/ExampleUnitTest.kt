package com.wei.sample

import io.reactivex.MaybeObserver
import io.reactivex.MaybeSource
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testRX() {
        var a = true
        Observable.just(a)
                .mergeWith(Observable.just(true))
                .subscribe(Consumer {
                    println("a$it")
                })
    }
}
