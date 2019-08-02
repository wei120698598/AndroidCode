package com.zanfou.component.platform.client.error


/**
 * v1.0 of the file created on 2019-08-02 by shuxin.wei, email: weishuxin@maoyan.com
 *
 * 错误响应体，[exception]中包含了可能有的异常，无论是Client、Server、NetWork等异常都会封装成[Throwable]；
 *
 * [message]中包含了错误信息，而[hitMessage]中包含了可以提示给用户的信息，[code]为异常码
 */

class ErrorBody(
    private var exception: Throwable? = null,
    private var message: String? = null,
    private var hitMessage: String? = null,
    private var code: Int? = null
) : IErrorBody {
    override fun getException(): Throwable? {
        return exception
    }

    override fun getHitMessage(): String? {
        return hitMessage
    }

    override fun getMessage(): String? {
        return message
    }

    override fun getCode(): Int? {
        return code
    }
}


interface IErrorBody {
    fun getException(): Throwable?

    fun getHitMessage(): String?

    fun getMessage(): String?

    fun getCode(): Int?

}