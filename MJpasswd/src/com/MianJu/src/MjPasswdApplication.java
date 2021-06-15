package com.MianJu.src;


import com.MianJu.src.GUI.Login;

public class MjPasswdApplication {

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                new Login();
            }
        };
        //开始运行程序
        Thread thread = new Thread(runnable,"master");
        thread.start();

    }
}
