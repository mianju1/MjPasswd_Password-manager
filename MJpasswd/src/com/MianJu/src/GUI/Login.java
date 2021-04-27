/*
 * Created by JFormDesigner on Fri Apr 23 14:44:33 CST 2021
 */

package com.MianJu.src.GUI;

import com.MianJu.Test.GUI.Function;
import com.MianJu.Test.GUI.LoginTools;
import com.MianJu.Test.GUI.RemindWin;
import com.MianJu.src.core.UserClass;
import com.MianJu.src.tools.LoginUser;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

/**
 * @author 1
 */
public class Login extends JFrame {

    public Login() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
    }

    private void login(){
        String userAccount = text_account.getText();
        String userPasswd = String.valueOf(passwordField_passwd.getPassword());
        UserClass userClass = new UserClass(userAccount,userPasswd);

        try {
            if (LoginUser.loginUser(userClass).isUserLoginBool()) {
                System.out.println("登录成功");
                if (checkBox_remberpasswd.isSelected()) {
                    LoginTools.createRemeberPasswd(userAccount, userPasswd);
                } else {
                    File file = new File(LoginTools.FILE_PATH);
                    if (file.exists()) {
                        file.delete();
                    }
                }
                loginwin.setVisible(false);

                Function function = new Function(userClass);

                RemindWin.instance(loginwin, "正在同步数据……请稍后");
            } else {
                RemindWin.instance(loginwin, "登录失败，请检查账号密码");
                System.out.println("弹窗：登录失败，请检查账号密码");
            }
        }catch (Exception exception){
            RemindWin.instance(loginwin, "网络连接失败，请检查网络设置");
            System.out.println("弹窗：网络连接失败，请检查网络设置");
        }
    }
        // ---------监听事件------------
    private void text_accountMousePressed(MouseEvent e) {//当鼠标按下账号编辑框
        image_account.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/编辑框_选中.png")));
        if (text_account.getText().equals("请输入账号")) {
            text_account.setText("");
        }
    }

    private void text_accountFocusLost(FocusEvent e) {//当鼠标失去账号编辑框焦点
        image_account.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/编辑框_未选中.png")));
        if (text_account.getText().equals("")){
            text_account.setText("请输入账号");
        }
    }

    private void passwordField_passwdMousePressed(MouseEvent e) {//当鼠标单击密码编辑框
        image_passwd.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/编辑框_选中.png")));
        if (String.valueOf(passwordField_passwd.getPassword()).equals("请输入密码")) {
            passwordField_passwd.setText("");
        }
    }

    private void passwordField_passwdFocusLost(FocusEvent e) {//当鼠标失去密码编辑框焦点
        image_passwd.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/编辑框_未选中.png")));
        if (String.valueOf(passwordField_passwd.getPassword()).isEmpty()){
            passwordField_passwd.setEchoChar((char)0);
            passwordField_passwd.setText("请输入密码");
        }
    }

    private void button_closeMouseClicked(MouseEvent e) {//当鼠标单击关闭按钮
        System.exit(0);
    }

    private void button_smailMouseClicked(MouseEvent e) {//当鼠标单击最小化
        loginwin.setExtendedState(ICONIFIED);
    }

    private void passwordField_passwdKeyPressed(KeyEvent e) {//在密码框按下键盘
        if (!(text_account.getText().isEmpty()) && !(String.valueOf(passwordField_passwd.getPassword()).isEmpty()) && !(text_account.getText().equals("请输入账号")) && !(String.valueOf(passwordField_passwd.getPassword()).equals("请输入密码"))){
            button_login.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/立即登录_可用.png")));
        }else{
            button_login.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/立即登录_不可用.png")));
        }
    }

    private void registerMouseClicked(MouseEvent e) {//点击未激活的注册
        tabbedPane1.setSelectedIndex(1);
    }

    private void login2MouseClicked(MouseEvent e) {//点击未激活的登录
        tabbedPane1.setSelectedIndex(0);
    }

    private void button_smail2MouseClicked(MouseEvent e) {
        loginwin.setExtendedState(ICONIFIED);
    }

    private void button_loginMousePressed(MouseEvent e) {//登录按钮按下
        if (!(text_account.getText().isEmpty()) && !(String.valueOf(passwordField_passwd.getPassword()).isEmpty()) && !(text_account.getText().equals("请输入账号")) && !(String.valueOf(passwordField_passwd.getPassword()).equals("请输入密码"))) {
            button_login.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/立即登录_按下.png")));
            login();
        }
    }

    private void button_loginMouseReleased(MouseEvent e) {//登录按钮弹起
        if (!(text_account.getText().isEmpty()) && !(String.valueOf(passwordField_passwd.getPassword()).isEmpty()) && !(text_account.getText().equals("请输入账号")) && !(String.valueOf(passwordField_passwd.getPassword()).equals("请输入密码"))) {
            button_login.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/立即登录_可用.png")));
        }
    }


    private void button_registerMousePressed(MouseEvent e) {//注册按钮按下
        if (!(text_account2.getText().isEmpty()) && !(String.valueOf(passwordField_passwd2.getPassword()).isEmpty()) && !(String.valueOf(passwordField_passwd3.getPassword()).isEmpty()) && !(text_email2.getText().isEmpty())) {
            button_register.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/立即注册_按下.png")));
        }
    }

    private void button_registerMouseReleased(MouseEvent e) {//注册按钮弹起
        if (!(text_account2.getText().isEmpty()) && !(String.valueOf(passwordField_passwd2.getPassword()).isEmpty()) && !(String.valueOf(passwordField_passwd3.getPassword()).isEmpty()) && !(text_email2.getText().isEmpty())) {
            button_register.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/立即注册_可用.png")));
        }
    }


    private void label_registerMouseClicked(MouseEvent e) {//单机注册标签，忘记密码下面
        tabbedPane1.setSelectedIndex(1);
    }

    private void button_registerMouseClicked(MouseEvent e) {//点击注册按钮
        if (!(text_account2.getText().isEmpty()) && !(String.valueOf(passwordField_passwd2.getPassword()).isEmpty()) &&
                !(String.valueOf(passwordField_passwd3.getPassword()).isEmpty()) && !(text_email2.getText().isEmpty())
                && !label_account_tips.isVisible() && !label_passwd_tips.isVisible() && !label_passwd_cheak_tips.isVisible()
         && !label_email_tips.isVisible()) {
            UserClass userClass = new UserClass(text_account2.getText(), String.valueOf(passwordField_passwd2.getPassword()), text_email2.getText());
            if (LoginUser.RegisterUser(userClass)) {
                System.out.println("注册成功");
            } else {
                System.out.println("弹窗：相同的账号或邮箱已存在");
            }
        }else {
            System.out.println("弹窗：请输入正确且完整的账号数据");
        }
    }

    private void passwordField_passwd3KeyReleased(KeyEvent e) {//注册界面确认密码框弹起键盘
        if (!(String.valueOf(passwordField_passwd2.getPassword()).equals(String.valueOf(passwordField_passwd3.getPassword())))){
            label_passwd_cheak_tips.setVisible(true);
        }else {
            label_passwd_cheak_tips.setVisible(false);
        }
    }

    private void passwordField_passwd2KeyReleased(KeyEvent e) {//注册界面密码框弹起键盘
        if (!(String.valueOf(passwordField_passwd2.getPassword()).matches("[^\\u4E00-\\u9FFF ]{6,14}"))){
            label_passwd_tips.setVisible(true);
        }else {
            label_passwd_tips.setVisible(false);
        }    }

    private void text_account2KeyReleased(KeyEvent e) {//注册界面账号框弹起键盘
        if (!(text_account2.getText().matches("[a-zA-Z0-9][^ ]{2,14}"))){
            label_account_tips.setVisible(true);
        }else {
            label_account_tips.setVisible(false);
        }
    }

    private void text_email2KeyReleased(KeyEvent e) {//在邮箱弹起键盘
        if (!(text_email2.getText().matches("^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$"))){
            label_email_tips.setVisible(true);
        }else {
            label_email_tips.setVisible(false);
        }

        if (!(label_email_tips.isVisible()) && !(label_account_tips.isVisible()) && !(label_passwd_tips.isVisible()) && !(label_passwd_cheak_tips.isVisible())) {
            button_register.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/立即注册_可用.png")));
        }
    }

    private void loginwinWindowOpened(WindowEvent e) {//登录窗口创建完毕
        //检查更新
        text_account.setText("请输入账号");

        if (new File(LoginTools.FILE_PATH).exists()){
            HashMap<String,String> getUserDate = LoginTools.readRemeberPasswd();
            checkBox_remberpasswd.setSelected(true);
            text_account.setText(getUserDate.get("account"));
            passwordField_passwd.setText(getUserDate.get("passwd"));

        }
    }

    private void label_image_MianJuMouseReleased(MouseEvent e) {//Bymianju头像放开
        try {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+new URL("http://mianj.xyz/"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void label_image_GiteeMouseReleased(MouseEvent e) {//Gitee头像放开
        try {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+new URL("https://gitee.com/mian_j/mj-passwd/"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }






    private void initComponents() {
//         JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
loginwin = new JFrame();
bujuban = new JPanel();
tabbedPane1 = new JTabbedPane();
Login_buju = new JPanel();
login = new JLabel();
register = new JLabel();
passwordField_passwd = new JPasswordField();
image_passwd = new JLabel();
text_account = new JTextField();
image_account = new JLabel();
button_login = new JButton();
label_register = new JLabel();
label_image_MianJu = new JLabel();
label_MianJu = new JLabel();
label_image_Gitee = new JLabel();
label_Gitee = new JLabel();
button_close = new JButton();
button_smail = new JButton();
checkBox_remberpasswd = new JCheckBox();
background = new JLabel();
Register_buju = new JPanel();
login2 = new JLabel();
register2 = new JLabel();
passwordField_passwd2 = new JPasswordField();
image_passwd2 = new JLabel();
text_account2 = new JTextField();
image_account2 = new JLabel();
button_register = new JButton();
label_image_MianJu2 = new JLabel();
label_MianJu2 = new JLabel();
label_image_Gitee2 = new JLabel();
label_Gitee2 = new JLabel();
button_close2 = new JButton();
button_smail2 = new JButton();
text_email2 = new JTextField();
image_account4 = new JLabel();
label_account = new JLabel();
label_passwd = new JLabel();
label_cheakpasswd = new JLabel();
label_email = new JLabel();
passwordField_passwd3 = new JPasswordField();
image_passwd3 = new JLabel();
label_account_tips = new JLabel();
label_passwd_tips = new JLabel();
label_passwd_cheak_tips = new JLabel();
label_email_tips = new JLabel();
background2 = new JLabel();

//======== loginwin ========
{
    loginwin.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    loginwin.setResizable(false);
    loginwin.setIconImage(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/logo.png")).getImage());
    loginwin.setUndecorated(true);
    loginwin.setVisible(true);
    loginwin.addWindowListener(new WindowAdapter() {
        @Override
        public void windowOpened(WindowEvent e) {
            loginwinWindowOpened(e);
        }
    });
    Container loginwinContentPane = loginwin.getContentPane();
    loginwinContentPane.setLayout(null);

    //======== bujuban ========
    {
        bujuban.setLayout(null);

        //======== tabbedPane1 ========
        {

            //======== Login_buju ========
            {
                Login_buju.setLayout(null);

                //---- login ----
                login.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/\u767b\u5f55_\u9009\u4e2d.png")));
                Login_buju.add(login);
                login.setBounds(new Rectangle(new Point(65, 75), login.getPreferredSize()));

                //---- register ----
                register.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/\u6ce8\u518c_\u672a\u9009\u4e2d.png")));
                register.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        registerMouseClicked(e);
                    }
                });
                Login_buju.add(register);
                register.setBounds(new Rectangle(new Point(185, 75), register.getPreferredSize()));

                //---- passwordField_passwd ----
                passwordField_passwd.setBorder(null);
                passwordField_passwd.setDisabledTextColor(new Color(95, 227, 251));
                passwordField_passwd.setForeground(new Color(173, 227, 251));
                passwordField_passwd.setFont(new Font("\u9ed1\u4f53", Font.PLAIN, 14));
                passwordField_passwd.setOpaque(false);
                passwordField_passwd.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                passwordField_passwd.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusLost(FocusEvent e) {
                        passwordField_passwdFocusLost(e);
                    }
                });
                passwordField_passwd.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        passwordField_passwdMousePressed(e);
                    }
                });
                passwordField_passwd.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        passwordField_passwdKeyPressed(e);
                    }
                });
                Login_buju.add(passwordField_passwd);
                passwordField_passwd.setBounds(45, 230, 280, 20);

                //---- image_passwd ----
                image_passwd.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/\u7f16\u8f91\u6846_\u672a\u9009\u4e2d.png")));
                Login_buju.add(image_passwd);
                image_passwd.setBounds(35, 215, image_passwd.getPreferredSize().width, 50);

                //---- text_account ----
                text_account.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                text_account.setBorder(null);
                text_account.setDisabledTextColor(new Color(95, 227, 251));
                text_account.setForeground(new Color(173, 227, 251));
                text_account.setFont(new Font("\u9ed1\u4f53", Font.PLAIN, 14));
                text_account.setOpaque(false);
                text_account.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusLost(FocusEvent e) {
                        text_accountFocusLost(e);
                    }
                });
                text_account.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        text_accountMousePressed(e);
                    }
                });
                Login_buju.add(text_account);
                text_account.setBounds(45, 165, 280, 20);

                //---- image_account ----
                image_account.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/\u7f16\u8f91\u6846_\u672a\u9009\u4e2d.png")));
                Login_buju.add(image_account);
                image_account.setBounds(35, 150, image_account.getPreferredSize().width, 50);

                //---- button_login ----
                button_login.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/\u7acb\u5373\u767b\u5f55_\u4e0d\u53ef\u7528.png")));
                button_login.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                button_login.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        button_loginMousePressed(e);
                    }
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        button_loginMouseReleased(e);
                    }
                });
                Login_buju.add(button_login);
                button_login.setBounds(35, 330, 300, 35);

                //---- label_register ----
                label_register.setText("\u6ca1\u6709\u8d26\u53f7\uff1f\u53bb\u6ce8\u518c\u4e00\u4e2a\uff01");
                label_register.setFont(new Font("\u9ed1\u4f53", Font.PLAIN, 14));
                label_register.setForeground(Color.cyan);
                label_register.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                label_register.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        label_registerMouseClicked(e);
                    }
                });
                Login_buju.add(label_register);
                label_register.setBounds(105, 395, 160, 25);

                //---- label_image_MianJu ----
                label_image_MianJu.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/MianJu.png")));
                label_image_MianJu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                label_image_MianJu.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        label_image_MianJuMouseReleased(e);
                    }
                });
                Login_buju.add(label_image_MianJu);
                label_image_MianJu.setBounds(65, 490, label_image_MianJu.getPreferredSize().width, 90);

                //---- label_MianJu ----
                label_MianJu.setText("By:MianJu");
                label_MianJu.setHorizontalAlignment(SwingConstants.CENTER);
                label_MianJu.setFont(new Font("\u9ed1\u4f53", Font.PLAIN, 16));
                label_MianJu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                Login_buju.add(label_MianJu);
                label_MianJu.setBounds(65, 590, 90, 25);

                //---- label_image_Gitee ----
                label_image_Gitee.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/giteelogo.png")));
                label_image_Gitee.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                label_image_Gitee.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        label_image_GiteeMouseReleased(e);
                    }
                });
                Login_buju.add(label_image_Gitee);
                label_image_Gitee.setBounds(new Rectangle(new Point(215, 490), label_image_Gitee.getPreferredSize()));

                //---- label_Gitee ----
                label_Gitee.setText("Gitee");
                label_Gitee.setHorizontalAlignment(SwingConstants.CENTER);
                label_Gitee.setFont(new Font("\u9ed1\u4f53", Font.PLAIN, 16));
                label_Gitee.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                Login_buju.add(label_Gitee);
                label_Gitee.setBounds(215, 590, 90, 25);

                //---- button_close ----
                button_close.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/fghsh.png")));
                button_close.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        button_closeMouseClicked(e);
                    }
                });
                Login_buju.add(button_close);
                button_close.setBounds(315, 0, 50, 30);

                //---- button_smail ----
                button_smail.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/zxh1.png")));
                button_smail.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        button_smailMouseClicked(e);
                    }
                });
                Login_buju.add(button_smail);
                button_smail.setBounds(265, 0, 50, 30);

                //---- checkBox_remberpasswd ----
                checkBox_remberpasswd.setText("\u8bb0\u4f4f\u5bc6\u7801");
                checkBox_remberpasswd.setOpaque(false);
                checkBox_remberpasswd.setBackground(Color.gray);
                checkBox_remberpasswd.setFont(new Font("\u9ed1\u4f53", Font.PLAIN, 14));
                checkBox_remberpasswd.setForeground(Color.orange);
                checkBox_remberpasswd.setHorizontalAlignment(SwingConstants.CENTER);
                Login_buju.add(checkBox_remberpasswd);
                checkBox_remberpasswd.setBounds(130, 285, 110, checkBox_remberpasswd.getPreferredSize().height);

                //---- background ----
                background.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/\u767b\u5f55\u9996\u9875.png")));
                background.setBackground(new Color(95, 227, 251));
                Login_buju.add(background);
                background.setBounds(5, 0, 360, background.getPreferredSize().height);
            }
            tabbedPane1.addTab("text", Login_buju);

            //======== Register_buju ========
            {
                Register_buju.setLayout(null);

                //---- login2 ----
                login2.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/\u767b\u5f55_\u672a\u9009\u4e2d.png")));
                login2.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        login2MouseClicked(e);
                    }
                });
                Register_buju.add(login2);
                login2.setBounds(65, 75, 120, 30);

                //---- register2 ----
                register2.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/\u6ce8\u518c_\u9009\u4e2d.png")));
                Register_buju.add(register2);
                register2.setBounds(185, 75, 120, 30);

                //---- passwordField_passwd2 ----
                passwordField_passwd2.setBorder(null);
                passwordField_passwd2.setDisabledTextColor(new Color(95, 227, 251));
                passwordField_passwd2.setForeground(new Color(173, 227, 251));
                passwordField_passwd2.setFont(new Font("\u9ed1\u4f53", Font.PLAIN, 14));
                passwordField_passwd2.setOpaque(false);
                passwordField_passwd2.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                passwordField_passwd2.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        passwordField_passwd2KeyReleased(e);
                    }
                });
                Register_buju.add(passwordField_passwd2);
                passwordField_passwd2.setBounds(45, 205, 280, 20);

                //---- image_passwd2 ----
                image_passwd2.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/\u7f16\u8f91\u6846_\u672a\u9009\u4e2d.png")));
                Register_buju.add(image_passwd2);
                image_passwd2.setBounds(35, 190, image_passwd2.getPreferredSize().width, 50);

                //---- text_account2 ----
                text_account2.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                text_account2.setBorder(null);
                text_account2.setDisabledTextColor(new Color(95, 227, 251));
                text_account2.setForeground(new Color(173, 227, 251));
                text_account2.setFont(new Font("\u9ed1\u4f53", Font.PLAIN, 14));
                text_account2.setOpaque(false);
                text_account2.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        text_account2KeyReleased(e);
                    }
                });
                Register_buju.add(text_account2);
                text_account2.setBounds(45, 145, 280, 20);

                //---- image_account2 ----
                image_account2.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/\u7f16\u8f91\u6846_\u672a\u9009\u4e2d.png")));
                Register_buju.add(image_account2);
                image_account2.setBounds(35, 130, 300, 50);

                //---- button_register ----
                button_register.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/\u7acb\u5373\u6ce8\u518c_\u4e0d\u53ef\u7528.png")));
                button_register.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                button_register.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        button_registerMouseClicked(e);
                    }
                    @Override
                    public void mousePressed(MouseEvent e) {
                        button_registerMousePressed(e);
                    }
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        button_registerMouseReleased(e);
                    }
                });
                Register_buju.add(button_register);
                button_register.setBounds(35, 390, 300, 35);

                //---- label_image_MianJu2 ----
                label_image_MianJu2.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/MianJu.png")));
                label_image_MianJu2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                Register_buju.add(label_image_MianJu2);
                label_image_MianJu2.setBounds(65, 490, 90, 90);

                //---- label_MianJu2 ----
                label_MianJu2.setText("By:MianJu");
                label_MianJu2.setHorizontalAlignment(SwingConstants.CENTER);
                label_MianJu2.setFont(new Font("\u9ed1\u4f53", Font.PLAIN, 16));
                label_MianJu2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                Register_buju.add(label_MianJu2);
                label_MianJu2.setBounds(65, 590, 90, 25);

                //---- label_image_Gitee2 ----
                label_image_Gitee2.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/giteelogo.png")));
                label_image_Gitee2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                Register_buju.add(label_image_Gitee2);
                label_image_Gitee2.setBounds(215, 490, 90, 90);

                //---- label_Gitee2 ----
                label_Gitee2.setText("Gitee");
                label_Gitee2.setHorizontalAlignment(SwingConstants.CENTER);
                label_Gitee2.setFont(new Font("\u9ed1\u4f53", Font.PLAIN, 16));
                label_Gitee2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                Register_buju.add(label_Gitee2);
                label_Gitee2.setBounds(215, 590, 90, 25);

                //---- button_close2 ----
                button_close2.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/fghsh.png")));
                button_close2.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        button_closeMouseClicked(e);
                    }
                });
                Register_buju.add(button_close2);
                button_close2.setBounds(315, 0, 50, 30);

                //---- button_smail2 ----
                button_smail2.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/zxh1.png")));
                button_smail2.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        button_smail2MouseClicked(e);
                    }
                });
                Register_buju.add(button_smail2);
                button_smail2.setBounds(265, 0, 50, 30);

                //---- text_email2 ----
                text_email2.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                text_email2.setBorder(null);
                text_email2.setDisabledTextColor(new Color(95, 227, 251));
                text_email2.setForeground(new Color(173, 227, 251));
                text_email2.setFont(new Font("\u9ed1\u4f53", Font.PLAIN, 14));
                text_email2.setOpaque(false);
                text_email2.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        text_email2KeyReleased(e);
                    }
                });
                Register_buju.add(text_email2);
                text_email2.setBounds(45, 330, 280, 20);

                //---- image_account4 ----
                image_account4.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/\u7f16\u8f91\u6846_\u672a\u9009\u4e2d.png")));
                Register_buju.add(image_account4);
                image_account4.setBounds(35, 315, image_account4.getPreferredSize().width, 50);

                //---- label_account ----
                label_account.setText("\u8d26\u53f7\uff1a");
                label_account.setFont(new Font("\u9ed1\u4f53", Font.PLAIN, 14));
                label_account.setForeground(Color.cyan);
                Register_buju.add(label_account);
                label_account.setBounds(35, 115, 45, 25);

                //---- label_passwd ----
                label_passwd.setText("\u5bc6\u7801\uff1a");
                label_passwd.setFont(new Font("\u9ed1\u4f53", Font.PLAIN, 14));
                label_passwd.setForeground(Color.cyan);
                Register_buju.add(label_passwd);
                label_passwd.setBounds(35, 180, 45, 25);

                //---- label_cheakpasswd ----
                label_cheakpasswd.setText("\u786e\u8ba4\u5bc6\u7801\uff1a");
                label_cheakpasswd.setFont(new Font("\u9ed1\u4f53", Font.PLAIN, 14));
                label_cheakpasswd.setForeground(Color.cyan);
                Register_buju.add(label_cheakpasswd);
                label_cheakpasswd.setBounds(35, 245, 80, 25);

                //---- label_email ----
                label_email.setText("\u90ae\u7bb1\uff1a");
                label_email.setFont(new Font("\u9ed1\u4f53", Font.PLAIN, 14));
                label_email.setForeground(Color.cyan);
                Register_buju.add(label_email);
                label_email.setBounds(35, 305, 45, 25);

                //---- passwordField_passwd3 ----
                passwordField_passwd3.setBorder(null);
                passwordField_passwd3.setDisabledTextColor(new Color(95, 227, 251));
                passwordField_passwd3.setForeground(new Color(173, 227, 251));
                passwordField_passwd3.setFont(new Font("\u9ed1\u4f53", Font.PLAIN, 14));
                passwordField_passwd3.setOpaque(false);
                passwordField_passwd3.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                passwordField_passwd3.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        passwordField_passwd3KeyReleased(e);
                    }
                });
                Register_buju.add(passwordField_passwd3);
                passwordField_passwd3.setBounds(45, 270, 280, 20);

                //---- image_passwd3 ----
                image_passwd3.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/\u7f16\u8f91\u6846_\u672a\u9009\u4e2d.png")));
                Register_buju.add(image_passwd3);
                image_passwd3.setBounds(35, 255, image_passwd3.getPreferredSize().width, 50);

                //---- label_account_tips ----
                label_account_tips.setText("\u8d26\u53f7\u7531\u4e3a3-15\u4e2a\u5b57\u7b26\u6570\u5b57\u7ec4\u6210");
                label_account_tips.setFont(new Font("\u9ed1\u4f53", Font.PLAIN, 14));
                label_account_tips.setForeground(Color.pink);
                label_account_tips.setHorizontalAlignment(SwingConstants.CENTER);
                label_account_tips.setVisible(false);
                Register_buju.add(label_account_tips);
                label_account_tips.setBounds(80, 110, 212, 25);

                //---- label_passwd_tips ----
                label_passwd_tips.setText("\u5bc6\u7801\u4e0d\u80fd\u5305\u542b\u4e2d\u6587\u548c\u7a7a\u683c\u4e146-15\u4f4d");
                label_passwd_tips.setFont(new Font("\u9ed1\u4f53", Font.PLAIN, 14));
                label_passwd_tips.setForeground(Color.pink);
                label_passwd_tips.setHorizontalAlignment(SwingConstants.CENTER);
                label_passwd_tips.setVisible(false);
                Register_buju.add(label_passwd_tips);
                label_passwd_tips.setBounds(65, 175, 241, 25);

                //---- label_passwd_cheak_tips ----
                label_passwd_cheak_tips.setText("\u4e24\u6b21\u8f93\u5165\u7684\u5bc6\u7801\u4e0d\u4e00\u81f4");
                label_passwd_cheak_tips.setFont(new Font("\u9ed1\u4f53", Font.PLAIN, 14));
                label_passwd_cheak_tips.setForeground(Color.pink);
                label_passwd_cheak_tips.setHorizontalAlignment(SwingConstants.CENTER);
                label_passwd_cheak_tips.setVisible(false);
                Register_buju.add(label_passwd_cheak_tips);
                label_passwd_cheak_tips.setBounds(65, 240, 241, 25);

                //---- label_email_tips ----
                label_email_tips.setText("\u90ae\u7bb1\u683c\u5f0f\u4e0d\u6b63\u786e");
                label_email_tips.setFont(new Font("\u9ed1\u4f53", Font.PLAIN, 14));
                label_email_tips.setForeground(Color.pink);
                label_email_tips.setHorizontalAlignment(SwingConstants.CENTER);
                label_email_tips.setVisible(false);
                Register_buju.add(label_email_tips);
                label_email_tips.setBounds(65, 300, 241, 25);

                //---- background2 ----
                background2.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/\u767b\u5f55\u9996\u9875.png")));
                background2.setBackground(new Color(95, 227, 251));
                Register_buju.add(background2);
                background2.setBounds(5, 0, 360, 640);
            }
            tabbedPane1.addTab("text", Register_buju);
        }
        bujuban.add(tabbedPane1);
        tabbedPane1.setBounds(-10, -30, 375, 670);
    }
    loginwinContentPane.add(bujuban);
    bujuban.setBounds(0, 0, 355, 640);

    loginwinContentPane.setPreferredSize(new Dimension(355, 640));
    loginwin.pack();
    loginwin.setLocationRelativeTo(loginwin.getOwner());
}
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JFrame loginwin;
    private JPanel bujuban;
    private JTabbedPane tabbedPane1;
    private JPanel Login_buju;
    private JLabel login;
    private JLabel register;
    private JPasswordField passwordField_passwd;
    private JLabel image_passwd;
    private JTextField text_account;
    private JLabel image_account;
    private JButton button_login;
    private JLabel label_register;
    private JLabel label_image_MianJu;
    private JLabel label_MianJu;
    private JLabel label_image_Gitee;
    private JLabel label_Gitee;
    private JButton button_close;
    private JButton button_smail;
    private JCheckBox checkBox_remberpasswd;
    private JLabel background;
    private JPanel Register_buju;
    private JLabel login2;
    private JLabel register2;
    private JPasswordField passwordField_passwd2;
    private JLabel image_passwd2;
    private JTextField text_account2;
    private JLabel image_account2;
    private JButton button_register;
    private JLabel label_image_MianJu2;
    private JLabel label_MianJu2;
    private JLabel label_image_Gitee2;
    private JLabel label_Gitee2;
    private JButton button_close2;
    private JButton button_smail2;
    private JTextField text_email2;
    private JLabel image_account4;
    private JLabel label_account;
    private JLabel label_passwd;
    private JLabel label_cheakpasswd;
    private JLabel label_email;
    private JPasswordField passwordField_passwd3;
    private JLabel image_passwd3;
    private JLabel label_account_tips;
    private JLabel label_passwd_tips;
    private JLabel label_passwd_cheak_tips;
    private JLabel label_email_tips;
    private JLabel background2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
