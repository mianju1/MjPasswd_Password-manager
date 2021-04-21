package com.MianJu.SQL;

import com.MianJu.config.Config;

import java.sql.*;

public class MysqlController  {

    Connection connection;
    Statement statement;

    public boolean commitMysqlDB(){
        try {

            //加载数据库驱动
            Class.forName(Config.JDBC_DRIVER);

            //连接mysql数据库
//            System.out.println("连接数据库……");
            connection = DriverManager.getConnection(Config.DB_URL,Config.USER_NAME,Config.USER_PASS);

            return true;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet selectDate(String sql) {
        /*
          执行查询语句于Mysql
         */
        if (commitMysqlDB()) {
            try {
                statement = connection.createStatement();
                return statement.executeQuery(sql);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return null;
            }
        }
        else {
            System.out.println("连接数据库失败！");
            return null;
        }
    }

    public boolean modifyDate(String sql) {
        /*
        执行修改数据语句
         */
        if (commitMysqlDB()) {
            try {
                statement = connection.createStatement();
                boolean b;
                b = statement.executeUpdate(sql) != 0;
                return b;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return false;
            }

        }else {
            System.out.println("连接数据库失败！");
            return false;
        }
    }

}