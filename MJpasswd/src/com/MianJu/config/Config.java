package com.MianJu.config;

public class Config {
    //jdbc驱动名和数据库地址，这里用的是mysql
    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "数据库地址";

    //声明数据库的用户名和密码
    public static final String USER_NAME = "数据库用户名";
    public static final String USER_PASS = "数据库密码";

    //加密的密钥和编码
    public static final String AES_CODE  = "UTF-8";
    public static final String KEY = "密钥";
}
