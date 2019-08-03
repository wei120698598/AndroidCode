package com.wei.component.platform.client.exception

/**
 * v1.0 of the file created on 2019-08-02 by shuxin.wei, email: weishuxin@maoyan.com
 *
 * 枚举错误码
 */
enum class ErrorCode(
    private var code: Int? = null,
    private var message: String? = null,
    private var hitMessage: String? = null
) {
    //通用提示(-1)
    UNKOWN_ERROR(-1, "未知错误", "未知错误"),

    //NetWorkError（100-999）
    NET_E_AUTH_FIALD(401, "Token无效，认证授权失败", "授权已过期，请重新登录"),
    NET_E_NOT_FOUND(404, "服务器页面找不到", "页面被喵星人弄丢了"),

    //ClientError（1000-9999）
    CLIENT_E_NET_DISCONNECTED(1001, "网络无连接，请求数据失败", "网络连接失败，请稍后重试"),

    //ServerError (10000-99999)
    SERVER_E_USER_NOT_FOUND(10001, "此用户已删除", "获取用户信息失败，请稍后刷新重试")


}