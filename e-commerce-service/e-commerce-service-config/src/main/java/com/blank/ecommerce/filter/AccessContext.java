package com.blank.ecommerce.filter;

import com.blank.ecommerce.vo.LoginUserInfo;

/**
 * 使用ThreadLocal保存用户信息
 * *** 要及时清理以保证
 * 1 内存不泄露
 * 2 数据不会混乱
 */
public class AccessContext {
    private static final ThreadLocal<LoginUserInfo> loginUserInfo = new ThreadLocal<>();
    public static LoginUserInfo getLoginUserInfo(){
        return loginUserInfo.get();
    }
    public static void setLoginUserInfo(LoginUserInfo _loginUserInfo){
        AccessContext.loginUserInfo.set(_loginUserInfo);
    }

    public static void clearLoginUserInfo() {
        loginUserInfo.remove();
    }

}
