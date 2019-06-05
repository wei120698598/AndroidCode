package com.wei.sample.handler

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.MessageQueue
import androidx.appcompat.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.TextView

/**
 * @description
 * @author shuxin.wei
 * @version v1.0.0
 * @date 2018/12/25
 * @email weishuxin@icourt.cc
 */
class HandlerActivity : Callback3, AppCompatActivity() {
    override fun callback2(msg: String?) {

    }

    override fun callback(msg: String) {

    }

    private var textView: TextView? = null

    private val callbackList = ArrayList<Callback3>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textView = TextView(this)
        textView?.layoutParams = ViewGroup.LayoutParams(-2, -2)
        textView?.text = "hhehhahha"
        setContentView(textView)

        val handler = object : Handler() {
            override fun handleMessage(msg: Message?) {
                println(msg?.obj.toString())
            }
        }
        Thread(Runnable {
            handler.sendMessage(handler.obtainMessage(1, "obtainMessage"))
            Looper.myQueue().addIdleHandler {
                println("lasjkfjalsdjflaj")
                false
            }
        }).start()

    }
}

