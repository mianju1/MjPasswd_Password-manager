package com.MianJu.src.tools;

import com.MianJu.SQL.MysqlController;
import com.MianJu.src.core.UserClass;
import com.MianJu.src.core.UserPasswdClass;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserPasswdDate {
    MysqlController mysqlController = new MysqlController();
    ResultSet resultSet;
    String sql;
    String passwdUserDate;

    /*
    创建一个存储账号密码
     */
    public boolean createUserPasswd(UserPasswdClass userPasswdClass){
        /*
        先查询存储数量，超过10个就禁止创建
         */
        //用户已拥有的数据量
        int userPasswdCount = getUserPasswdCount(userPasswdClass);
        //用户最大拥有数据量，默认是10
        int maxUserPasswdCount = userPasswdClass.getMaxUserCountPasswdClass();

        if (userPasswdCount >= maxUserPasswdCount){
            return false;
        }
        if (userPasswdClass.isLoginStatusUserPswd()) {

            //给密码文本加密
            passwdUserDate = EncryptDate.AES_Encrypt(userPasswdClass.getPasswd(),userPasswdClass.getUSER_KEY());

            //创建
            sql = String.format("INSERT INTO `passwddate`(`p_order`,`u_id`,`p_zhanghao`,`p_mima`,`p_shuyu`) " +
                    "VALUES('%s','%s','%s','%s','%s')", getUserPasswdCount(userPasswdClass) + 1,userPasswdClass.getUserId(), userPasswdClass.getAccount(),passwdUserDate, userPasswdClass.getFromWeb());
            boolean createUserPasswdDate = mysqlController.modifyDate(sql);

            sql = String.format("UPDATE `userdate` SET `u_count` = '%d' WHERE `u_id` = '%s'", userPasswdCount + 1, userPasswdClass.getUserId());

//不需要再更新这个p_order的数据，之前插入了正确的数据就可以了

            boolean modifyUserCountDate = mysqlController.modifyDate(sql);

            return createUserPasswdDate && modifyUserCountDate;
        }
        return false;
    }

    public boolean deleteUserPasswd(UserClass userClass, int index){
        /*
        删除一个用户存储的数据(通过序号删除)
         */
        try {
            sql = String.format("DELETE FROM `passwddate` WHERE `p_autoid` = %d", getUsersAutoID(userClass, index));
            mysqlController.modifyDate(sql);
            sql = String.format("UPDATE `userdate` SET `u_count` = '%d' WHERE `u_id` = '%s'", getUserPasswdCount(userClass) - 1, userClass.getUserId());
            mysqlController.modifyDate(sql);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUserPasswd(UserClass userClass,String createTime){
        /*
        删除一个用户存储的数据(通过创建时间)
         */
        try {
            sql = String.format("DELETE FROM `passwddate` WHERE `p_time` = '%s' AND `u_id` = '%s'",createTime,userClass.getUserId());
            mysqlController.modifyDate(sql);
            sql = String.format("UPDATE `userdate` SET `u_count` = '%d' WHERE `u_id` = '%s'", getUserPasswdCount(userClass) - 1, userClass.getUserId());
            mysqlController.modifyDate(sql);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }



    private int getUserPasswdCount(UserPasswdClass userPasswdClass){
        sql = String.format("SELECT * FROM userdate WHERE u_id REGEXP '%s'",userPasswdClass.getUserId());
        resultSet = mysqlController.selectDate(sql);
        try {
            if (resultSet.next()){
                return resultSet.getInt("u_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    private int getUserPasswdCount(UserClass userClass){
        sql = String.format("SELECT * FROM userdate WHERE u_id REGEXP '%s'",userClass.getUserId());
        resultSet = mysqlController.selectDate(sql);
        try {
            if (resultSet.next()){
                return resultSet.getInt("u_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    private int getUsersAutoID(UserClass userClass,int index){
        sql = String.format("SELECT * FROM passwddate WHERE u_id = '%s' AND p_order = %d", userClass.getUserId(),index);
        resultSet = mysqlController.selectDate(sql);
        try {
            if (resultSet.next()){
                return resultSet.getInt("p_autoid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    private int getUserAutoID(UserClass userClass){
        int res = 0;
        sql = String.format("SELECT * FROM passwddate WHERE u_id = '%s'", userClass.getUserId());
        resultSet = mysqlController.selectDate(sql);
        try {
            while (resultSet.next()){
               res = resultSet.getInt("p_autoid");
            }
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
