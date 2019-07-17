package com.wei.sample.coroutines

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.wei.sample.R
import com.wei.sample.utils.logD
import com.wei.sample.utils.logI
import kotlinx.android.synthetic.main.activity_coroutines.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.broadcast
import org.jetbrains.anko.*
import org.jetbrains.anko.doAsyncResult
import java.lang.Thread.currentThread
import java.lang.ref.WeakReference
import java.util.concurrent.Future

class CoroutinesActivity : AppCompatActivity() {
    private val mainScope = MainScope()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines)
        button.setOnClickListener {
            //            Thread(Runnable {
//                Thread.sleep(6000)
//                text_view.layoutParams = ViewGroup.LayoutParams(-1, -1)
//                text_view.text = Thread.currentThread().name
//            }).start()
//            GlobalScope.launch {
//                val job = GlobalScope.launch {
//                    // 启动一个新协程并保持对这个作业的引用
//                    Logger.logD("1111111!")
//                    delay(6000L)
//                    Logger.logD("World!")
//                }
//                Logger.logD("Hello,")
//                job.doAsync {
//                    this.uiThread {
//                        text_view.layoutParams = ViewGroup.LayoutParams(-1, -1)
//                    }
//                } // 等待直到子协程执行结束
//                Logger.logD("Hello World!")
//            }

//            runBlocking {
//                val job = launch {
//                    try {
//                        repeat(1000) { i ->
//                            "job: I'm sleeping $i ...".logI()
//                            delay(500L)
//                        }
//                    } finally {
//                        delay(3000L)
//                    }
//                }
//                delay(2000L) // 延迟一段时间
//                "main: I'm tired of waiting!".logI()
//                job.cancelAndJoin() // 取消该作业
//                "main: Now I can quit.".logI()
//            }
//
//
//            GlobalScope.launch(Dispatchers.Main, CoroutineStart.LAZY) {
//                delay(1000)
//            }
//
//            CoroutineScope(Dispatchers.Main).launch {
//                delay(1000)
//            }
//
//            GlobalScope.async {
//                withContext(Dispatchers.Main) {
//
//                }
//            }
//
//            runBlocking {
//                val await = async {
//                    return@async 1
//                }.await()
//            }


//            runBlocking<Unit> {
//                val time = measureTimeMillis {
//                    val one = doSomethingUsefulOne()
//                    val two = doSomethingUsefulTwo()
//                    println("The answer is ${one + two}")
//                }
//                println("Completed in $time ms")
//            }

            val progressDialog = ProgressDialog(this@CoroutinesActivity)

            GlobalScope.launch {
                withContext(Dispatchers.Main) {
                    printThread("222")
                    progressDialog.show()
                }
                printThread("000")
                val async = withContext(coroutineContext) {
                    printThread("111")
                    delay(6000)
                    "111"
                }
                printThread("444")
                withContext(Dispatchers.Main) {
                    text_view.text = async.toString()
                    text_view.layoutParams = LinearLayout.LayoutParams(-1, -1)
                    printThread("333")
                    progressDialog.dismiss()
                }
                printThread("555")
            }

            val doAsync = GlobalScope.doAsync {
                printThread("333")
                "1212"
            }

            val doAsyncResult = GlobalScope.doAsyncResult{
                printThread("4444")
                 "111"
            }

            AnkoAsyncContext(WeakReference(this@CoroutinesActivity)).activityUiThread {

            }


            CoroutineScope(Dispatchers.IO).launch {

            }
            printThread(doAsyncResult.get())


//            Thread(Runnable {
//                val progressDialog = ProgressDialog(this@CoroutinesActivity)
//                runOnUiThread {
//                    progressDialog.show()
//                }
//                Thread.sleep(6000)
//                runOnUiThread {
//                    progressDialog.dismiss()
//                }
//            }).start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

internal fun printThread(tag: String) {
    "$tag: ThreadName:${currentThread().name} ThreadId: ${currentThread().id}".logD("shuxin.wei")
}

suspend fun doSomethingUsefulOne(): Int {
    Thread.currentThread().name.logI()
    delay(1000L) // 假设我们在这里做了些有用的事
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    Thread.currentThread().name.logI("")
    var adf: String? = null
    adf.logI()
    delay(1000L) // 假设我们在这里也做了一些有用的事
    return 29
}

class Person(var name: String)

operator fun Person.plus(p: Person): Person {
    this.name += p.name
    return this
}