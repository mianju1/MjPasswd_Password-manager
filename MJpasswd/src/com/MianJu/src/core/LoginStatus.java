package com.MianJu.src.core;

public class LoginStatus {
    private String userID;
    private boolean  userLoginBool;

    public LoginStatus(UserClass userClass) {
        //登录以后确认登录状态，如果true，就赋值，然后其他地方调用比较userID是否相等
        this.userLoginBool = userClass.getUserLoginStatus();
        this.userID = userClass.getUserId();

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public boolean isUserLoginBool() {
        return userLoginBool;
    }

    public void setUserLoginBool(boolean userLoginBool) {
        this.userLoginBool = userLoginBool;
    }
}
