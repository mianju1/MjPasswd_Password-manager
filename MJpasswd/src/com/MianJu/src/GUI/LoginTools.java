package com.MianJu.src.GUI;

import sun.nio.cs.CharsetMapping;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LoginTools {
    public static final String FILE_PATH = System.getProperty("java.io.tmpdir") + "Mj-passwd.temp";

    public static HashMap<String,String> readRemeberPasswd(){
        if (new File(FILE_PATH).exists()) {
            HashMap<String, String> readUserDate = new HashMap<>(2);
            try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
                String userAccount = br.readLine().split("= ")[1];
                String userPasswd = br.readLine().split("= ")[1];
                readUserDate.put("account",userAccount);
                readUserDate.put("passwd",userPasswd);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return readUserDate;
        }
        return null;
    }

    public static boolean createRemeberPasswd(String account , String passwd){
        String content = "UserAccount = " + account + "\n" + "UserPasswd = "+passwd;
        File file = new File(FILE_PATH);
        try (FileOutputStream fileOutputStream = new FileOutputStream(FILE_PATH)){
            if (!file.exists()) {
                file.createNewFile();
            }
            byte[] contentByte = content.getBytes(StandardCharsets.UTF_8);
            fileOutputStream.write(contentByte);
            fileOutputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
