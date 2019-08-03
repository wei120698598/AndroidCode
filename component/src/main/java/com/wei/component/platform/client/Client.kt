package com.wei.component.platform.client

import com.wei.component.platform.client.exception.IExceptionBody
import okhttp3.ConnectionPool
import java.util.*

object Client {
    /**
     * 通用Http连接池
     */
    var SIMPLE_CONNECTION_POOL = ConnectionPool()
    /**
     * 图片请求连接池
     */
    var IMAGE_CONNECTION_POOL = ConnectionPool()
    /**
     * IO超时时间
     */
    var SOCKET_WRITE_TIME_OUT = 15_000L
    /**
     * 连接超时时间
     */
    var SOCKET_TIME_OUT = 15_000L
    /**
     * 响应超时时间
     */
    var SOCKET_RESPONSE_TIME_OUT = 15_000L
    /**
     * Http缓存大小为10M
     */
    var CACHE_SIZE = 10 * 1_024L * 1_024L

    var IS_AUTH = true

    private val exceptions: MutableSet<(IExceptionBody)> = Collections.synchronizedSet(HashSet<(IExceptionBody)>())


    fun registerExceptionListener(block: (IExceptionBody)) {
        exceptions.add(block)
    }

    fun unRegisterExceptionListener() {

    }
}