package com.wei.sample.handler

import android.os.Bundle
import android.os.Handler
import android.os.Message
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

        val callback: Callback3? = object : Callback3 {
            override fun callback2(msg: String?) {

            }

            override fun callback(msg: String?) {
                println("shuxin.wei callback:" + Thread.currentThread().name)
                textView?.text = msg
            }
        }


        object : Thread() {
            override fun run() {
                if (callback != null) {
                    callbackList.add(callback)
                }
                println("shuxin.wei run:" + Thread.currentThread().name)

                val handler = object : Handler() {
                    override fun handleMessage(msg: Message?) {
                        super.handleMessage(msg)
                    }
                }

                handler.sendMessage(Message.obtain().apply {
                    obj = ""
                })

            }
        }.start()
    }
}

