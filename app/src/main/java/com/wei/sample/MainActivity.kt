package com.wei.sample

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.util.HashMap




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main()
    }


    fun main() {
        println("地址1:"+buildTaskDetailUrl("1"))
        val query = HashMap<String, String>()
        query["id"] = "1"
        query["id"] = "哈哈"
        println("地址2:"+buildUrl("/third/task.html", query))
    }

    /**
     * 构建任务详情路由地址
     *
     * @param taskId 任务id
     * @return 地址
     */
    fun buildTaskDetailUrl(taskId: String): String {
        try {
            val builder = Uri.Builder()
            val parse = Uri.parse("https://alphalawyer.cn/")
            builder.scheme(parse.scheme)
            builder.encodedAuthority(parse.authority)
            builder.path("/third/task.html")
            builder.encodedQuery("id2=哈哈")
            return builder.build().toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    fun buildUrl(path: String, query: HashMap<String, String>?): String {
        try {
            val builder = Uri.Builder()
            val parse = Uri.parse("https://alphalawyer.cn/")
            builder.scheme(parse.scheme)
            builder.encodedAuthority(parse.authority)
            builder.encodedPath(path)
            if (query != null) {
                for ((key, value) in query) {
                    builder.appendQueryParameter(key, value)
                }
            }
            return builder.build().toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }
}
