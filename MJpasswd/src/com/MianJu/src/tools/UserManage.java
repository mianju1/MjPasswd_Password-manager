package com.MianJu.src.tools;

import com.MianJu.SQL.MysqlController;
import com.MianJu.config.Config;
import com.MianJu.src.core.UserClass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class UserManage extends MysqlController {
    public boolean registerUser(UserClass userClass){
        /*
        注册用户
         */
        if (!(userClass.getName().matches("[a-zA-Z0-9][^ ]{2,15}"))){
            System.out.println("账号需要2-15个英文或数字字符且不能有空格");
        }else if (!(userClass.getPasswd().matches("[^\\u4E00-\\u9FFF ]{6,15}"))){
            System.out.println("密码需要不为中文6-15个字符且不能有空格");
        }else {
            String sql = String.format("SELECT * FROM userdate WHERE u_name REGEXP '%s'",userClass.getName());
            try {
                if (super.selectDate(sql).next()){
                    System.out.println("相同的名称已存在，请重新输入一个名称");
                    return false;
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

            createUserId(userClass);
            return true;
        }
        return false;
    }

    public boolean cheakUserLoginStatus(UserClass userClass) {
        /*
        登录方法
         */
        ResultSet res = super.selectDate(String.format("SELECT * FROM userdate WHERE u_name REGEXP '%s'",userClass.getName()));
        try {
            //从传入的name中寻找这一行，然后判断对应的密码，如果匹配就成功登录
            if (res.next()){
                if (res.getString("u_passwd").equals(userClass.getPasswd()) && res.getString("u_name").equals(userClass.getName())) {
                    userClass.setUserLoginStatus(true);
                    return true;
                }else {
                    System.out.println("账号或密码错误");
                }
            }else {
                System.out.println("账号不存在");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        userClass.setUserLoginStatus(false);
        return false;
    }

    private static String createUserId(UserClass userClass){
        /*
        随机生成一个用户的ID号
         */
        Random random = new Random();
        //生成一个随机的用户ID
        long l = (long)(100000000 + Math.random() * (999999999 - 100000000 + 1));
        userClass.setUserId(Long.toString(l));
        MysqlController mysqlController = new MysqlController();
        //查询这个用户ID是否存在
        ResultSet resultSet = mysqlController.selectDate(String.format("SELECT * FROM userdate WHERE u_id REGEXP '%s'",userClass.getUserId_local()));
        try {
            //存在的话，重新执行，再生成一个id
            if (resultSet.next()){
                if (resultSet.getString("u_id").equals(userClass.getUserId())){
                    userClass.setUserId(createUserId(userClass));
                }
            }else {
                /*
                如果不是重复的就进行插入操作
                 */

                //密钥格式：用户ID+用户密码+用户ID的第3位数字
                String userKey = userClass.getUserId_local() + userClass.getPasswd() + userClass.getUserId_local().charAt(2);

                //然后将密钥再一层加密，表层密钥在配置文件
                userKey = EncryptDate.AES_Encrypt(userKey, Config.KEY);
                String sql = String.format("REPLACE INTO userdate(`u_id`,`u_name`,`u_passwd`,`u_createtime`,`u_key`) VALUE('%s','%s','%s',%s,'%s')",userClass.getUserId_local(),userClass.getName(),userClass.getPasswd(),"CURRENT_TIMESTAMP",userKey);
                mysqlController.modifyDate(sql);
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return userClass.getUserId_local();
    }

}
