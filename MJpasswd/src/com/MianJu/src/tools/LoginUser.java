package com.MianJu.src.tools;

import com.MianJu.src.core.LoginStatus;
import com.MianJu.src.core.UserClass;
import com.MianJu.src.tools.UserManage;

public class LoginUser{
    public static LoginStatus loginUser(UserClass userClass){
        UserManage userManage = new UserManage();
        //先让用户控制中心将这个确认登录状态
        userManage.cheakUserLoginStatus(userClass);
        //然后返回这个登录状态类
        return new LoginStatus(userClass);
    }
    public static boolean RegisterUser(UserClass userClass){
        UserManage userManage = new UserManage();
        return userManage.registerUser(userClass);
    }
}
