package com.wei.sample

import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.wei.sample.design.CoordinatorLayoutActivity
import com.wei.sample.floatbtn.FloatButtonActivity
import com.wei.sample.floatbtn.FloatToast
import com.wei.sample.handler.HandlerActivity
import com.wei.sample.mvvm.TaskActivity
import com.wei.sample.recyclerview.RecyclerViewActivity
import com.wei.sample.rxjava.RxJavaActivity
import com.wei.sample.thread.ThreadActivity
import com.wei.sample.xposed.XposedActivity
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.util.*


class MainActivity : ListActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val itemList = Arrays.asList("Handler", "RxJava", "检查root", "Xposed", "悬浮窗", "RecyclerView","Coordinator","MVVM","ThreadTest")
        listAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, itemList)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        when (position) {
            0 -> startActivity(Intent(this, HandlerActivity::class.java))
            1 -> startActivity(Intent(this, RxJavaActivity::class.java))
            2 -> Toast.makeText(this, "root权限:" + checkRoot(), Toast.LENGTH_SHORT).show()
            3 -> startActivity(Intent(this, XposedActivity::class.java))
            4 -> FloatToast(this)
            5 -> startActivity(Intent(this, RecyclerViewActivity::class.java))
            6 -> startActivity(Intent(this, CoordinatorLayoutActivity::class.java))
            7 -> startActivity(Intent(this, TaskActivity::class.java))
            8 -> startActivity(Intent(this, ThreadActivity::class.java))
        }
    }

    private fun checkRoot(): Boolean {
        val binPath = "/system/bin/su";
        val xBinPath = "/system/xbin/su";
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
            e.printStackTrace();
        } finally {
            process?.destroy()
        }
        return false
    }
}
