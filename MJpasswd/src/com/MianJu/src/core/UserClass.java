package com.MianJu.src.core;

import com.MianJu.SQL.MysqlController;
import com.MianJu.config.Config;
import com.MianJu.src.tools.EncryptDate;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserClass {
    private String userId;
    private String name;
    private String passwd;
    private boolean userLoginStatus;
    private int count;
    private String key;
    private int maxUserCount;
    private String emailUser;

    public UserClass(String name, String passwd, String emailUser) {
        this.name = name;
        this.passwd = passwd;
        this.emailUser = emailUser;
    }

    public UserClass(String name, String passwd) {
        this.name = name;
        this.passwd = passwd;
    }

    public String getUserId() {
        if (userLoginStatus) {
            if (userId == null) {
                String sql = String.format("SELECT * FROM userdate WHERE u_name REGEXP '%s'", name);
                ResultSet resultSet = new MysqlController().selectDate(sql);
                try {
                    if (resultSet.next()) {
                        return resultSet.getString("u_id");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return userId;
        }else {
            System.out.println("请先登录！");
            return userId;
        }
    }

    public String getKey() {
        if (userLoginStatus) {
            String sql = String.format("SELECT * FROM userdate WHERE u_id REGEXP '%s'", getUserId());
            ResultSet resultSet = new MysqlController().selectDate(sql);
            try {
                if (resultSet.next()) {
//                    EncryptDate.AES_Decrypt()
                    String encrypt = resultSet.getString("u_key");
                    //返回解码后的用户密钥，密钥格式：用户ID+用户密码+用户ID的第3位数字
                    return EncryptDate.AES_Decrypt(encrypt, Config.KEY);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return key;
        }else {
            System.out.println("请先登录！");
            return key;
        }
    }

    public boolean isUserLoginStatus() {
        return userLoginStatus;
    }

    public int getCount() {
        if (userLoginStatus) {
            String sql = String.format("SELECT * FROM userdate WHERE u_id REGEXP '%s'", getUserId());
            ResultSet resultSet = new MysqlController().selectDate(sql);
            try {
                if (resultSet.next()) {
                    return resultSet.getInt("u_count");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return count;
        }else {
            System.out.println("请先登录！");
            return count;
        }
    }

    public String getEmailUser_local(){
        return emailUser;
    }

    public String getEmailUser() {
        {
            if (userLoginStatus) {
                String sql = String.format("SELECT * FROM userdate WHERE u_id REGEXP '%s'", getUserId());
                ResultSet resultSet = new MysqlController().selectDate(sql);
                try {
                    if (resultSet.next()) {
                        emailUser = resultSet.getString("u_email");
                        //返回解码后的用户密钥，密钥格式：用户ID+用户密码+用户ID的第3位数字
                        return emailUser;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return emailUser;
            }else {
                System.out.println("请先登录！");
                return emailUser;
            }
        }
    }

    public int getMaxUserCount() {
        if (userLoginStatus) {
            String sql = String.format("SELECT * FROM userdate WHERE u_id REGEXP '%s'", getUserId());
            ResultSet resultSet = new MysqlController().selectDate(sql);
            try {
                if (resultSet.next()) {
                    maxUserCount = resultSet.getInt("u_maxcount");
                    //返回解码后的用户密钥，密钥格式：用户ID+用户密码+用户ID的第3位数字
                    return maxUserCount;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return maxUserCount;
        }else {
            System.out.println("请先登录！");
            return maxUserCount;
        }
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId_local (){
        //为了获得本地id，非获取服务器id，不需要登录
        return this.userId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public boolean getUserLoginStatus() {
        return userLoginStatus;
    }

    public void setUserLoginStatus(boolean userLoginStatus) {
        this.userLoginStatus = userLoginStatus;
    }

    @Override
    public String toString() {
        return "UserClass{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", passwd='" + passwd + '\'' +
                ", userLoginStatus=" + userLoginStatus +
                ", count=" + count +
                '}';
    }
}
