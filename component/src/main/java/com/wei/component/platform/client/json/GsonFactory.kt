package com.wei.component.platform.client.json

import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * v1.0 of the file created on 2019-08-02 by shuxin.wei, email: weishuxin@maoyan.com
 * 创建通用[Gson]实例
 */
class GsonFactory : IGsonFactory {
    override fun createGson(): Gson =
        GsonBuilder()
            .setLenient()// json宽松
            .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
            .setPrettyPrinting()// 调教格式
            .disableHtmlEscaping() //默认是GSON把HTML 转义的
            .registerTypeAdapter(String::class.java, Null2StringAdapter())//将空字符串转换成""
            .create()
}

interface IGsonFactory {
    fun createGson(): Gson
}