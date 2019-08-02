package com.wei.sample

import android.app.ListActivity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.wei.sample.coroutines.CoroutinesActivity
import com.wei.sample.design.CoordinatorLayoutActivity
import com.wei.sample.floatbtn.FloatButtonActivity
import com.wei.sample.floatbtn.FloatToast
import com.wei.sample.handler.HandlerActivity
import com.wei.sample.mvvm.TaskActivity
import com.wei.sample.recyclerview.AsyncListUtilActivity
import com.wei.sample.recyclerview.RecyclerViewActivity
import com.wei.sample.rxbus.RxBus2
import com.wei.sample.rxjava.RxJavaActivity
import com.wei.sample.thread.ThreadActivity
import com.wei.sample.ue.UEActivity
import com.wei.sample.xposed.XposedActivity
import io.reactivex.functions.Consumer
import java.io.*
import java.net.URI
import java.util.*


typealias Predicate<T> = (T) -> Boolean
typealias rredicate =  MainActivity.() -> Boolean


class MainActivity : ListActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val itemList = Arrays.asList("Handler", "RxJava", "检查root", "Xposed", "悬浮窗", "RecyclerView",
                "AsyncListUtilActivity", "Coordinator", "MVVM", "ThreadTest", "协程", "UE", "Command")
        listAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, itemList)


        RxBus2.getInstance().toObservable(String::class.java).subscribe(Consumer {
            Log.i("shuxin.wei", it.toString())
        })
        RxBus2.getInstance().toObservable().subscribe(Consumer {
            Log.i("shuxin.wei", "111" + it.toString())
        })

    }



    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        when (position) {
            0 -> startActivity(Intent(this, HandlerActivity::class.java))
            1 -> startActivity(Intent(this, RxJavaActivity::class.java))
            2 -> Toast.makeText(this, "root权限:" + checkRoot(), Toast.LENGTH_SHORT).show()
            3 -> startActivity(Intent(this, XposedActivity::class.java))
            4 -> startActivity(Intent(this, FloatButtonActivity::class.java))
            5 -> startActivity(Intent(this, RecyclerViewActivity::class.java))
            6 -> startActivity(Intent(this, AsyncListUtilActivity::class.java))
            7 -> startActivity(Intent(this, CoordinatorLayoutActivity::class.java))
            8 -> startActivity(Intent(this, TaskActivity::class.java))
            9 -> startActivity(Intent(this, ThreadActivity::class.java))
            10 -> startActivity(Intent(this, CoroutinesActivity::class.java))
            11 -> startActivity(Intent(this, UEActivity::class.java))

            12 -> {
                val thread = Thread {
                    execute("su -V")
                    execute("adb shell setprop debug.hwui.overdraw show")
                }
            }
        }
    }


    fun copy(name: String): Thread {
        return Thread()
    }

    private fun checkRoot(): Boolean {
        val binPath = "/system/bin/su"
        val xBinPath = "/system/xbin/su"
        if (File(binPath).exists() && isCanExecute(binPath)) {
            return true
        }
        if (File(xBinPath).exists() && isCanExecute(xBinPath)) {
            return true
        }
        return false
    }

    private fun isCanExecute(filePath: String): Boolean {
        var process: Process? = null
        try {
            process = Runtime.getRuntime().exec("ls -l $filePath")
            val bufferedReader = BufferedReader(InputStreamReader(process.inputStream))
            val readLine: String = bufferedReader.readLine()
            if (readLine.length >= 4) {
                val flag = readLine[3]
                if (flag == 's' || flag == 'x')
                    return true
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            process?.destroy()
        }
        return false
    }

    private fun execute(command: String) {
        var process: Process? = null
        var bufferedReader: BufferedReader? = null
        var errorBufferedReader: BufferedReader? = null
        try {
            process = Runtime.getRuntime().exec(command)
            bufferedReader = BufferedReader(InputStreamReader(process.inputStream))
            errorBufferedReader = BufferedReader(InputStreamReader(process.errorStream))

            val os = DataOutputStream(process.outputStream)
            os.writeBytes("$command\n")
            os.writeBytes("exit\n")
            os.flush()

            val result = StringBuffer()
            val error = StringBuffer()

            while (true) {
                val readLine = bufferedReader.readLine()
                if (readLine != null) {
                    result.append(readLine)
                } else {
                    break
                }
            }
            while (true) {
                val readLine = errorBufferedReader.readLine()
                if (readLine != null) {
                    error.append(readLine)
                } else {
                    break
                }
            }
            println("shuxin.wei exec: $command")
//            println("shuxin.wei result:${process.exitValue()}  $result")
            println("shuxin.wei error: $error")
            process.waitFor()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            bufferedReader?.close()
            errorBufferedReader?.close()
            process?.destroy()
        }
    }

}
