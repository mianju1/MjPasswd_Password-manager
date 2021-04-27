package com.MianJu.src.core;

import com.MianJu.SQL.MysqlController;
import com.MianJu.src.tools.EncryptDate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserPasswdClass {
    private String userId;
    private String account;
    private String passwd;
    private String fromWeb;
    private String createTime;
    private int order;
    private boolean loginStatusUserPswd;
    private int maxUserCountPasswdClass;

    private final String USER_KEY;


    public UserPasswdClass(List<UserPasswdClass> list, UserClass userClass) {
        /*
        同步数据库的构造函数
         */

        //因为这两个参数是用户数据表中，所以放在同步数据方法外
        this.USER_KEY = userClass.getKey();
        this.maxUserCountPasswdClass = userClass.getMaxUserCount();

        synDate(list,userClass);
    }

    public UserPasswdClass(UserClass userClass, String account, String passwd) {
        this.userId = userClass.getUserId();
        this.account = account;
        this.passwd = passwd;
        this.loginStatusUserPswd = userClass.getUserLoginStatus();
        this.USER_KEY = userClass.getKey();
        this.maxUserCountPasswdClass = userClass.getMaxUserCount();

    }

    public UserPasswdClass(UserClass userClass, String account, String passwd, String fromWeb) {
        this.userId = userClass.getUserId();
        this.account = account;
        this.passwd = passwd;
        this.fromWeb = fromWeb;
        this.loginStatusUserPswd = userClass.getUserLoginStatus();
        this.USER_KEY = userClass.getKey();
        this.maxUserCountPasswdClass = userClass.getMaxUserCount();
    }

    public UserPasswdClass(String userId, String account, String passwd, String fromWeb, String createTime, int order) {
        /*
        全参构造
         */
        this.userId = userId;
        this.account = account;
        this.passwd = passwd;
        this.fromWeb = fromWeb;
        this.createTime = createTime;
        this.order = order;
        this.USER_KEY = null;
        this.maxUserCountPasswdClass = 10;
    }

    public boolean synDate(List<UserPasswdClass> list,UserClass userClass){
        /*
        同步数据库的函数
         */
        String sql;
//        ArrayList<UserPasswdClass> userPasswdClasses = new ArrayList<>();
        list.clear();//每一次同步清空数据
        MysqlController mysqlController = new MysqlController();

        sql = String.format("SELECT * FROM `passwddate` WHERE u_id = '%s'",userClass.getUserId());
        ResultSet resultSet = mysqlController.selectDate(sql);
        try {
            while (resultSet.next()){
                //对回来的密文密码进行解码
                this.passwd = EncryptDate.AES_Decrypt(resultSet.getString("p_mima"),userClass.getKey());

                this.account = resultSet.getString("p_zhanghao");
                this.userId = resultSet.getString("u_id");
                this.fromWeb = resultSet.getString("p_shuyu");
                this.order = resultSet.getInt("p_order");
                this.createTime = resultSet.getString("p_time");
                list.add(new UserPasswdClass(userId,account,passwd,fromWeb,createTime,order));
            }
            return true;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public String getUSER_KEY() {
        return USER_KEY;
    }

    public String getUserId() {
        return userId;
    }

    public boolean isLoginStatusUserPswd() {
        return loginStatusUserPswd;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setLoginStatusUserPswd(boolean loginStatusUserPswd) {
        this.loginStatusUserPswd = loginStatusUserPswd;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getFromWeb() {
        return fromWeb;
    }

    public int getMaxUserCountPasswdClass() {
        return maxUserCountPasswdClass;
    }

    @Override
    public String toString() {
        return "UserPasswdClass{" +
                "userId='" + userId + '\'' +
                ", account='" + account + '\'' +
                ", passwd='" + passwd + '\'' +
                ", fromWeb='" + fromWeb + '\'' +
                ", createTime='" + createTime + '\'' +
                ", order=" + order +
                ", loginStatusUserPswd=" + loginStatusUserPswd +
                '}';
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setFromWeb(String fromWeb) {
        this.fromWeb = fromWeb;
    }

}
