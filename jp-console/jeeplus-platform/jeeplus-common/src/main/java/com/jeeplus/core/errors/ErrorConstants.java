package com.jeeplus.core.errors;


public final class ErrorConstants {

    public static final String LOGIN_ERROR_FORBIDDEN = "该帐号已经被禁止登录！";
    public static final String LOGIN_ERROR_INCORRECT = "用户名或者密码错误！";
    public static final String LOGIN_ERROR_NOTFOUND = "用户名不存在!";
    public static final String LOGIN_ERROR_NOT_LOGIN = "你没有登录系统，请先登录！";
    public static final String LOGIN_ERROR_EXPIRED = "您的登录已过期，请重新登录！";
    public static final String LOGIN_ERROR_TIMEOUT = "您的token已经超时，请刷新token重新获取！";
    public static final String LOGIN_ERROR_FORBID_LOGGED_IN_ELSEWHERE = "您的账号已在其它地方登录，您被禁止登录！";
    public static final String LOGIN_ERROR__KICK_OUT_LOGGED_IN_ELSEWHERE = "您的账号在另一台设备上登录,如非本人操作，请立即修改密码！";
    public static final String LOGIN_ERROR_ERROR_VALIDATE_CODE = "您输入的验证码不正确，请重新输入！";
}
