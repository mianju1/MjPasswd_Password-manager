/*
 * Created by JFormDesigner on Sun Apr 25 17:29:24 CST 2021
 */

package com.MianJu.src.GUI;

import com.MianJu.SQL.MysqlController;
import com.MianJu.Test.GUI.RemindWin;
import com.MianJu.src.core.UserClass;
import com.MianJu.src.core.UserPasswdClass;
import com.MianJu.src.tools.UserPasswdDate;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

/**
 * @author 1
 */
public class Function {
    boolean isDrag;
    static Point origin = new Point();
    private UserClass userClass;
    List<UserPasswdClass> userPasswdDateServer = new ArrayList<>();
    private UserPasswdClass userPasswdClass;


    public Function() {
        initComponents();
    }

    public Function(UserClass userClass) {
        this.userClass = userClass;
        userPasswdClass = new UserPasswdClass(userPasswdDateServer,userClass);
        initComponents();
        this.table_copy.getTableHeader().setReorderingAllowed(false);//设置表不可拖动

    }

    public boolean setIntoClipboard(String data) {//设置到粘贴板
        try {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(data),null);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean synchDate(){//同步云端数据
//        RemindWin.instance(FunctionWin,"正在同步数据……请稍后");


        try {
            userPasswdClass.synDate(userPasswdDateServer, userClass);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        int rows = table_copy.getRowCount();//获取当前表中行数
        DefaultTableModel tableModel = (DefaultTableModel) table_copy.getModel();
        if(userClass.getUserLoginStatus() && userPasswdDateServer!=null){

            //循环清空表
            for (int i = 0; i < rows; i++) {
                tableModel.removeRow(0);
            }

            //添加数据到表
            for (UserPasswdClass passwdClass : userPasswdDateServer) {
                if(passwdClass.getFromWeb().equals("null")){//如果网站为空就替换为空字符串
                    passwdClass.setFromWeb("");
                }
                tableModel.addRow(new String[]{passwdClass.getAccount(),passwdClass.getPasswd(),passwdClass.getFromWeb()
                ,passwdClass.getCreateTime()});
            }

            //同步个人数据
            textField_email.setText(userClass.getEmailUser());
            label_hasdate.setText("当前拥有数据：" + userClass.getCount());
            label_maxdate.setText("拥有最大数据量：" + userClass.getMaxUserCount());


            RemindWin.instance(FunctionWin,"数据同步完成");
            return true;
        }
        RemindWin.instance(FunctionWin,"数据同步失败，请检查网络");
        return false;
    }

    public boolean addDate(){//添加数据
        UserPasswdClass addPasswdClass;
        if (textField_addfromweb.getText().isEmpty()) {
            addPasswdClass = new UserPasswdClass(userClass,textField_addaccount.getText(),
                    textField_addpasswd.getText());//网站为空情况
        }else {
            addPasswdClass = new UserPasswdClass(userClass,textField_addaccount.getText(),
                    textField_addpasswd.getText(),textField_addfromweb.getText());//网站不为空情况
        }
        UserPasswdDate addDate = new UserPasswdDate();
        if (addDate.createUserPasswd(addPasswdClass)){
            RemindWin.instance(FunctionWin,"数据添加成功，点击同步查看");
            return true;
        }else if (userClass.getMaxUserCount() == userClass.getCount()){
            RemindWin.instance(FunctionWin,"拥有数据达到最大数量，添加失败");
            return false;
        }
        RemindWin.instance(FunctionWin,"添加数据失败，请检查网络");
        return false;
    }

    public boolean delDate(int index){//删除数据
        UserPasswdDate userPasswdDate = new UserPasswdDate();

        //根据时间删除数据
        if (userPasswdDate.deleteUserPasswd(userClass,table_copy.getValueAt(index,3).toString())) {
            System.out.println("弹窗：数据删除成功，请点击同步数据刷新");
            RemindWin.instance(FunctionWin,"数据删除成功，请点击同步数据刷新");
            return true;
        }else {
            System.out.println("弹窗：数据删除失败，请检查网络或同步数据");
            RemindWin.instance(FunctionWin,"数据删除失败，请检查网络或同步数据");
        }
        return false;
    }

    public boolean modfilyEmail(String email){
        MysqlController mysqlController = new MysqlController();
        String sql = String.format("UPDATE userdate SET u_email = '%s' WHERE u_id = '%s'",email,userClass.getUserId());
        return mysqlController.modifyDate(sql);
    }

    public boolean modfilyPasswd(String passwd){
        MysqlController mysqlController = new MysqlController();
        String sql = String.format("UPDATE userdate SET u_passwd = '%s' WHERE u_id = '%s'",passwd,userClass.getUserId());
        return mysqlController.modifyDate(sql);
    }

            //    ---------事件----------


    private void panel_menuMouseDragged(MouseEvent e) {//鼠标按住拖动
        isDrag = true;
    }

    private void panel_menuMousePressed(MouseEvent e) {//鼠标按下
        origin.x = e.getX();
        origin.y = e.getY();
    }

    private void panel_menuMouseReleased(MouseEvent e) {//放开，移动窗口
        if (isDrag) {
            Point p = FunctionWin.getLocation();
            FunctionWin.setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
            isDrag = false;
        }
    }


    private void label_select3MouseReleased(MouseEvent e) {//弹起查询数据
        tabbedPane1.setSelectedIndex(0);
        label_select.setSize(156, 65);
        label_aboutus.setSize(155, 65);
        label_my.setSize(155, 65);
    }

    private void label_aboutus3MouseReleased(MouseEvent e) {//弹起关于作者
        tabbedPane1.setSelectedIndex(1);
        label_aboutus.setSize(156, 65);
        label_select.setSize(155, 65);
        label_my.setSize(155, 65);
    }

    private void label_my3MouseReleased(MouseEvent e) {//弹起个人账号
        tabbedPane1.setSelectedIndex(2);
        label_aboutus.setSize(155, 65);
        label_select.setSize(155, 65);
        label_my.setSize(156, 65);
    }

    private void button_closeMouseReleased(MouseEvent e) {//弹起关闭按钮
        System.exit(0);
    }

    private void button_minMouseReleased(MouseEvent e) {//弹起最小化按钮
        FunctionWin.setExtendedState(Frame.ICONIFIED);
    }


    private void table_copyMouseReleased(MouseEvent e) {//右键弹出菜单
        if (e.isPopupTrigger() && table_copy.getSelectedRow() != -1){
            popupMenu1.show(table_copy,e.getX(),e.getY());
        }
    }

    private void button3MouseClicked(MouseEvent e) {//点击复制账号
        if (!setIntoClipboard(table_copy.getValueAt(table_copy.getSelectedRow(), 0).toString())){//如果复制到粘贴板失败弹窗
            System.out.println("弹窗：复制失败");
            RemindWin.instance(FunctionWin,"复制失败");
        }
    }

    private void button4MouseClicked(MouseEvent e) {//点击复制密码
        if (!setIntoClipboard(table_copy.getValueAt(table_copy.getSelectedRow(), 1).toString())){
            System.out.println("弹窗：复制失败");
            RemindWin.instance(FunctionWin,"复制失败");
        }
    }






    private void button_copyWebMouseClicked(MouseEvent e) {//点击复制网站
        if (!setIntoClipboard(table_copy.getValueAt(table_copy.getSelectedRow(), 2).toString())){
            System.out.println("弹窗：复制失败");
            RemindWin.instance(FunctionWin,"复制失败");
        }
    }

    private void button_synDateMouseClicked(MouseEvent e) {//点击菜单同步数据
        synchDate();
    }

    private void label1MouseClicked(MouseEvent e) {//点击同步云端数据
        synchDate();
    }

    private void label_addDateMouseClicked(MouseEvent e) {//单击添加数据
        panel1.setVisible(true);
    }

    private void label_delDateMouseClicked(MouseEvent e) {//单机删除数据
        if (table_copy.getSelectedRow() != -1){
            delDate(table_copy.getSelectedRow());
        }
    }

    private void label_mywebMouseReleased(MouseEvent e) {//抬起面具个人网站
        try {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+new URL("http://mianj.xyz/"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void label_giteewebMouseReleased(MouseEvent e) {//抬起MjPasswd码云网站
        try {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+new URL("https://gitee.com/mian_j/mj-passwd/"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void label_modemailMouseClicked(MouseEvent e) {//单击修改邮箱
        if (label_modemail.getText().equals("修改邮箱")) {
            label_modemail.setText("确定");
            textField_email.setEditable(true);
        } else if (label_modemail.getText().equals("确定") && textField_email.getText().matches("^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")) {
            label_modemail.setText("修改邮箱");
            textField_email.setEditable(false);
            if (modfilyEmail(textField_email.getText())) {
                System.out.println("弹窗：邮箱修改成功");
                RemindWin.instance(FunctionWin, "邮箱修改成功！");
            }

        } else {
            System.out.println("弹窗:输入邮箱有误，请重新输入");
            RemindWin.instance(FunctionWin, "输入邮箱有误，请重新输入");
        }
    }

    private void label_modpasswdMouseClicked(MouseEvent e) {//单击修改密码
        if (label_modpasswd.getText().equals("修改密码")){
            label_modpasswd.setText("确定");
            textField_passwd.setEditable(true);
            textField_passwd.setText("");
        }else if (label_modpasswd.getText().equals("确定") && textField_passwd.getText().matches("[^\\u4E00-\\u9FFF ]{6,14}")){
            label_modpasswd.setText("修改密码");
            textField_passwd.setEditable(false);
            //再修改数据库中密码
            if (modfilyPasswd(textField_passwd.getText())) {
                System.out.println("弹窗：密码修改成功！");
                RemindWin.instance(FunctionWin,"密码修改成功！");
            }
            textField_passwd.setText("**********");
        }else {
            System.out.println("弹窗：密码输入错误");
            RemindWin.instance(FunctionWin,"请输入6-15个不包含汉字的字符");
        }
    }

    private void label_cheakMouseClicked(MouseEvent e) {//点击确认添加数据
        if (!textField_addaccount.getText().isEmpty() && !textField_addpasswd.getText().isEmpty()) {
            addDate();
        }else{
            System.out.println("弹窗：请把数据填写完毕");
            RemindWin.instance(FunctionWin,"请把数据填写完毕");
        }
    }

    private void FunctionWinWindowOpened(WindowEvent e) {//创建好窗口后
        if (!synchDate()) {
            RemindWin.instance(FunctionWin,"数据同步失败，请检查网络！");
        }
    }

    private void button_delDateMouseReleased(MouseEvent e) {//放开右键菜单删除
        if (table_copy.getSelectedRow() != -1){
            delDate(table_copy.getSelectedRow());
        }
    }



    private void initComponents() {

        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        FunctionWin = new JFrame();
        label_aboutus = new JLabel();
        label_my = new JLabel();
        label_select = new JLabel();
        Funselect3 = new JDesktopPane();
        panel_menu3 = new JPanel();
        button_min = new JButton();
        button_close = new JButton();
        label_logo = new JLabel();
        menuBar3 = new JMenuBar();
        tabbedPane1 = new JTabbedPane();
        查询数据 = new JPanel();
        panel_date = new JPanel();
        scrollPane1 = new JScrollPane();
        table_copy = new JTable();
        label_synDate = new JLabel();
        label_addDate = new JLabel();
        label_delDate = new JLabel();
        panel1 = new JPanel();
        textField_addaccount = new JTextField();
        textField_addpasswd = new JTextField();
        textField_addfromweb = new JTextField();
        label_cheak = new JLabel();
        label_account = new JLabel();
        label_passwd = new JLabel();
        label_fromweb = new JLabel();
        关于作者 = new JPanel();
        label_aboutLogo = new JLabel();
        label_myweb = new JLabel();
        label_giteeweb = new JLabel();
        个人账号 = new JPanel();
        desktopPane_email = new JDesktopPane();
        textField_email = new JTextField();
        label_nowemail = new JLabel();
        label_modemail = new JLabel();
        desktopPane_Passwd = new JDesktopPane();
        textField_passwd = new JTextField();
        label4 = new JLabel();
        label_modpasswd = new JLabel();
        label_hasdate = new JLabel();
        label_maxdate = new JLabel();
        popupMenu1 = new JPopupMenu();
        button_copyAccount = new JButton();
        button_copyPasswd = new JButton();
        button_copyWeb = new JButton();
        button_synDate = new JButton();
        button_delDate = new JButton();

        //======== FunctionWin ========
        {
            FunctionWin.setUndecorated(true);
            FunctionWin.setVisible(true);
            FunctionWin.setIconImage(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/logo.png")).getImage());
            FunctionWin.addWindowListener(new WindowAdapter() {
                @Override
                public void windowOpened(WindowEvent e) {
                    FunctionWinWindowOpened(e);
                }
            });
            Container FunctionWinContentPane = FunctionWin.getContentPane();
            FunctionWinContentPane.setLayout(null);

            //---- label_aboutus ----
            label_aboutus.setText("\u5173 \u4e8e \u4f5c \u8005");
            label_aboutus.setHorizontalAlignment(SwingConstants.CENTER);
            label_aboutus.setBackground(Color.white);
            label_aboutus.setOpaque(true);
            label_aboutus.setFont(new Font("\u7b49\u7ebf Light", Font.PLAIN, 16));
            label_aboutus.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            label_aboutus.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    label_aboutus3MouseReleased(e);
                }
            });
            FunctionWinContentPane.add(label_aboutus);
            label_aboutus.setBounds(0, 97, 155, 65);

            //---- label_my ----
            label_my.setText("\u4e2a \u4eba \u8d26 \u53f7");
            label_my.setHorizontalAlignment(SwingConstants.CENTER);
            label_my.setOpaque(true);
            label_my.setBackground(Color.white);
            label_my.setFont(new Font("\u7b49\u7ebf Light", Font.PLAIN, 16));
            label_my.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            label_my.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    label_my3MouseReleased(e);
                }
            });
            FunctionWinContentPane.add(label_my);
            label_my.setBounds(0, 163, 155, 65);

            //---- label_select ----
            label_select.setHorizontalAlignment(SwingConstants.CENTER);
            label_select.setText("\u6570 \u636e \u4e2d \u5fc3");
            label_select.setBackground(Color.white);
            label_select.setOpaque(true);
            label_select.setFont(new Font("\u7b49\u7ebf Light", Font.PLAIN, 16));
            label_select.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            label_select.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    label_select3MouseReleased(e);
                }
            });
            FunctionWinContentPane.add(label_select);
            label_select.setBounds(0, 31, 156, 65);

            //======== Funselect3 ========
            {
                Funselect3.setBackground(new Color(222, 222, 222));
            }
            FunctionWinContentPane.add(Funselect3);
            Funselect3.setBounds(0, 30, 156, 560);

            //======== panel_menu3 ========
            {
                panel_menu3.setBackground(Color.white);
                panel_menu3.addMouseMotionListener(new MouseMotionAdapter() {
                    @Override
                    public void mouseDragged(MouseEvent e) {
                        panel_menuMouseDragged(e);
                    }
                });
                panel_menu3.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        panel_menuMousePressed(e);
                    }
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        panel_menuMouseReleased(e);
                    }
                });
                panel_menu3.setLayout(null);

                //---- button_min ----
                button_min.setBorder(null);
                button_min.setBorderPainted(false);
                button_min.setBackground(new Color(235, 235, 235));
                button_min.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/\u6700\u5c0f\u5316.png")));
                button_min.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        button_minMouseReleased(e);
                    }
                });
                panel_menu3.add(button_min);
                button_min.setBounds(810, 0, 45, 30);

                //---- button_close ----
                button_close.setBorderPainted(false);
                button_close.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/\u5173\u95ed.png")));
                button_close.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        button_closeMouseReleased(e);
                    }
                });
                panel_menu3.add(button_close);
                button_close.setBounds(855, 0, 45, 30);

                //---- label_logo ----
                label_logo.setBackground(Color.white);
                label_logo.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/logo.png")));
                panel_menu3.add(label_logo);
                label_logo.setBounds(10, -5, 30, 40);

                //======== menuBar3 ========
                {
                    menuBar3.setBackground(new Color(235, 235, 235));
                }
                panel_menu3.add(menuBar3);
                menuBar3.setBounds(40, -5, 770, 40);
            }
            FunctionWinContentPane.add(panel_menu3);
            panel_menu3.setBounds(0, 0, 900, 30);

            //======== tabbedPane1 ========
            {

                //======== 查询数据 ========
                {
                    查询数据.setBackground(Color.white);
                    查询数据.setLayout(null);

                    //======== panel_date ========
                    {
                        panel_date.setLayout(null);

                        //======== scrollPane1 ========
                        {

                            //---- table_copy ----
                            table_copy.setFont(new Font("\u65b9\u6b63\u59da\u4f53", Font.PLAIN, 18));
                            table_copy.setModel(new DefaultTableModel(
                                new Object[][] {
                                },
                                new String[] {
                                    "\u8d26\u53f7", "\u5bc6\u7801", "\u6240\u5c5e\u7f51\u7ad9", "\u521b\u5efa\u65f6\u95f4"
                                }
                            ) {
                                boolean[] columnEditable = new boolean[] {
                                    false, false, false, false
                                };
                                @Override
                                public boolean isCellEditable(int rowIndex, int columnIndex) {
                                    return columnEditable[columnIndex];
                                }
                            });
                            table_copy.setSelectionBackground(new Color(51, 116, 130));
                            table_copy.setRowHeight(25);
                            table_copy.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseReleased(MouseEvent e) {
                                    table_copyMouseReleased(e);
                                }
                            });
                            scrollPane1.setViewportView(table_copy);
                        }
                        panel_date.add(scrollPane1);
                        scrollPane1.setBounds(0, 0, 660, 305);

                        {
                            // compute preferred size
                            Dimension preferredSize = new Dimension();
                            for(int i = 0; i < panel_date.getComponentCount(); i++) {
                                Rectangle bounds = panel_date.getComponent(i).getBounds();
                                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                            }
                            Insets insets = panel_date.getInsets();
                            preferredSize.width += insets.right;
                            preferredSize.height += insets.bottom;
                            panel_date.setMinimumSize(preferredSize);
                            panel_date.setPreferredSize(preferredSize);
                        }
                    }
                    查询数据.add(panel_date);
                    panel_date.setBounds(65, 15, 660, 305);

                    //---- label_synDate ----
                    label_synDate.setText("\u540c\u6b65\u4e91\u7aef\u6570\u636e");
                    label_synDate.setBorder(new LineBorder(Color.lightGray));
                    label_synDate.setHorizontalAlignment(SwingConstants.CENTER);
                    label_synDate.setForeground(new Color(102, 102, 102));
                    label_synDate.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
                    label_synDate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    label_synDate.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            label1MouseClicked(e);
                        }
                    });
                    查询数据.add(label_synDate);
                    label_synDate.setBounds(85, 350, 120, 40);

                    //---- label_addDate ----
                    label_addDate.setText("\u6dfb\u52a0\u6570\u636e");
                    label_addDate.setBorder(new LineBorder(Color.lightGray));
                    label_addDate.setHorizontalAlignment(SwingConstants.CENTER);
                    label_addDate.setForeground(new Color(102, 102, 102));
                    label_addDate.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
                    label_addDate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    label_addDate.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            label_addDateMouseClicked(e);
                        }
                    });
                    查询数据.add(label_addDate);
                    label_addDate.setBounds(230, 350, 120, 40);

                    //---- label_delDate ----
                    label_delDate.setText("\u5220\u9664\u6570\u636e");
                    label_delDate.setBorder(new LineBorder(Color.lightGray));
                    label_delDate.setHorizontalAlignment(SwingConstants.CENTER);
                    label_delDate.setForeground(new Color(102, 102, 102));
                    label_delDate.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
                    label_delDate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    label_delDate.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            label_delDateMouseClicked(e);
                        }
                    });
                    查询数据.add(label_delDate);
                    label_delDate.setBounds(375, 350, 120, 40);

                    //======== panel1 ========
                    {
                        panel1.setBackground(Color.white);
                        panel1.setVisible(false);
                        panel1.setLayout(null);
                        panel1.add(textField_addaccount);
                        textField_addaccount.setBounds(5, 40, 130, 30);
                        panel1.add(textField_addpasswd);
                        textField_addpasswd.setBounds(150, 40, 130, 30);
                        panel1.add(textField_addfromweb);
                        textField_addfromweb.setBounds(295, 40, 130, 30);

                        //---- label_cheak ----
                        label_cheak.setText("\u786e\u8ba4\u6dfb\u52a0");
                        label_cheak.setBorder(new LineBorder(Color.lightGray));
                        label_cheak.setHorizontalAlignment(SwingConstants.CENTER);
                        label_cheak.setForeground(new Color(102, 102, 102));
                        label_cheak.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
                        label_cheak.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        label_cheak.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                label_cheakMouseClicked(e);
                            }
                        });
                        panel1.add(label_cheak);
                        label_cheak.setBounds(435, 40, 70, 30);

                        //---- label_account ----
                        label_account.setText("\u8d26\u53f7");
                        label_account.setBorder(null);
                        label_account.setHorizontalAlignment(SwingConstants.CENTER);
                        label_account.setForeground(new Color(102, 102, 102));
                        label_account.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
                        panel1.add(label_account);
                        label_account.setBounds(35, 5, 70, 30);

                        //---- label_passwd ----
                        label_passwd.setText("\u5bc6\u7801");
                        label_passwd.setBorder(null);
                        label_passwd.setHorizontalAlignment(SwingConstants.CENTER);
                        label_passwd.setForeground(new Color(102, 102, 102));
                        label_passwd.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
                        panel1.add(label_passwd);
                        label_passwd.setBounds(180, 5, 70, 30);

                        //---- label_fromweb ----
                        label_fromweb.setText("\u7f51\u7ad9( \u53ef\u7a7a )");
                        label_fromweb.setBorder(null);
                        label_fromweb.setHorizontalAlignment(SwingConstants.CENTER);
                        label_fromweb.setForeground(new Color(102, 102, 102));
                        label_fromweb.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
                        panel1.add(label_fromweb);
                        label_fromweb.setBounds(325, 5, 75, 30);
                    }
                    查询数据.add(panel1);
                    panel1.setBounds(75, 405, 515, 85);
                }
                tabbedPane1.addTab("text", 查询数据);

                //======== 关于作者 ========
                {
                    关于作者.setBackground(Color.white);
                    关于作者.setLayout(null);

                    //---- label_aboutLogo ----
                    label_aboutLogo.setHorizontalAlignment(SwingConstants.CENTER);
                    label_aboutLogo.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/\u5927Logo.jpg")));
                    关于作者.add(label_aboutLogo);
                    label_aboutLogo.setBounds(220, 15, 290, 280);

                    //---- label_myweb ----
                    label_myweb.setText("\u9762\u5177\u7684\u4e2a\u4eba\u7f51\u7ad9\uff1ahttp://mianj.xyz/");
                    label_myweb.setHorizontalAlignment(SwingConstants.CENTER);
                    label_myweb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    label_myweb.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 16));
                    label_myweb.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseReleased(MouseEvent e) {
                            label_mywebMouseReleased(e);
                        }
                    });
                    关于作者.add(label_myweb);
                    label_myweb.setBounds(165, 305, 400, 65);

                    //---- label_giteeweb ----
                    label_giteeweb.setText("MjPasswd gitee\uff1ahttps://gitee.com/mian_j/mj-passwd");
                    label_giteeweb.setHorizontalAlignment(SwingConstants.CENTER);
                    label_giteeweb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    label_giteeweb.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 16));
                    label_giteeweb.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseReleased(MouseEvent e) {
                            label_giteewebMouseReleased(e);
                        }
                    });
                    关于作者.add(label_giteeweb);
                    label_giteeweb.setBounds(133, 370, 465, 65);
                }
                tabbedPane1.addTab("text", 关于作者);

                //======== 个人账号 ========
                {
                    个人账号.setBackground(Color.white);
                    个人账号.setLayout(null);

                    //======== desktopPane_email ========
                    {
                        desktopPane_email.setBackground(Color.white);

                        //---- textField_email ----
                        textField_email.setFont(new Font("\u9ed1\u4f53", Font.PLAIN, 16));
                        textField_email.setBorder(new LineBorder(Color.lightGray));
                        textField_email.setEditable(false);
                        desktopPane_email.add(textField_email, JLayeredPane.DEFAULT_LAYER);
                        textField_email.setBounds(90, 0, 240, 30);

                        //---- label_nowemail ----
                        label_nowemail.setText("\u5f53\u524d\u90ae\u7bb1\uff1a");
                        label_nowemail.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 16));
                        label_nowemail.setHorizontalAlignment(SwingConstants.LEFT);
                        desktopPane_email.add(label_nowemail, JLayeredPane.DEFAULT_LAYER);
                        label_nowemail.setBounds(0, 0, 85, 30);
                    }
                    个人账号.add(desktopPane_email);
                    desktopPane_email.setBounds(135, 45, 375, 30);

                    //---- label_modemail ----
                    label_modemail.setText("\u4fee\u6539\u90ae\u7bb1");
                    label_modemail.setHorizontalAlignment(SwingConstants.CENTER);
                    label_modemail.setBorder(new LineBorder(Color.lightGray));
                    label_modemail.setForeground(new Color(102, 102, 102));
                    label_modemail.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    label_modemail.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            label_modemailMouseClicked(e);
                        }
                    });
                    个人账号.add(label_modemail);
                    label_modemail.setBounds(515, 45, 65, 30);

                    //======== desktopPane_Passwd ========
                    {
                        desktopPane_Passwd.setBackground(Color.white);

                        //---- textField_passwd ----
                        textField_passwd.setFont(new Font("\u9ed1\u4f53", Font.PLAIN, 16));
                        textField_passwd.setBorder(new LineBorder(Color.lightGray));
                        textField_passwd.setEditable(false);
                        textField_passwd.setText("**********");
                        desktopPane_Passwd.add(textField_passwd, JLayeredPane.DEFAULT_LAYER);
                        textField_passwd.setBounds(90, 0, 240, 30);

                        //---- label4 ----
                        label4.setText("\u5f53\u524d\u5bc6\u7801\uff1a");
                        label4.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 16));
                        label4.setHorizontalAlignment(SwingConstants.LEFT);
                        desktopPane_Passwd.add(label4, JLayeredPane.DEFAULT_LAYER);
                        label4.setBounds(0, 0, 85, 30);
                    }
                    个人账号.add(desktopPane_Passwd);
                    desktopPane_Passwd.setBounds(135, 95, 375, 30);

                    //---- label_modpasswd ----
                    label_modpasswd.setText("\u4fee\u6539\u5bc6\u7801");
                    label_modpasswd.setHorizontalAlignment(SwingConstants.CENTER);
                    label_modpasswd.setBorder(new LineBorder(Color.lightGray));
                    label_modpasswd.setForeground(new Color(102, 102, 102));
                    label_modpasswd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    label_modpasswd.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            label_modpasswdMouseClicked(e);
                        }
                    });
                    个人账号.add(label_modpasswd);
                    label_modpasswd.setBounds(515, 95, 65, 30);

                    //---- label_hasdate ----
                    label_hasdate.setText("\u5f53\u524d\u62e5\u6709\u6570\u636e\uff1a");
                    label_hasdate.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 16));
                    label_hasdate.setHorizontalAlignment(SwingConstants.LEFT);
                    个人账号.add(label_hasdate);
                    label_hasdate.setBounds(135, 145, 365, 30);

                    //---- label_maxdate ----
                    label_maxdate.setText("\u62e5\u6709\u6700\u5927\u6570\u636e\u91cf\uff1a");
                    label_maxdate.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 16));
                    label_maxdate.setHorizontalAlignment(SwingConstants.LEFT);
                    个人账号.add(label_maxdate);
                    label_maxdate.setBounds(135, 195, 365, 30);
                }
                tabbedPane1.addTab("text", 个人账号);
            }
            FunctionWinContentPane.add(tabbedPane1);
            tabbedPane1.setBounds(125, 0, 785, 605);

            FunctionWinContentPane.setPreferredSize(new Dimension(900, 593));
            FunctionWin.pack();
            FunctionWin.setLocationRelativeTo(FunctionWin.getOwner());
        }

        //======== popupMenu1 ========
        {

            //---- button_copyAccount ----
            button_copyAccount.setText("\u590d\u5236\u8d26\u53f7");
            button_copyAccount.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
            button_copyAccount.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    button3MouseClicked(e);
                }
            });
            popupMenu1.add(button_copyAccount);

            //---- button_copyPasswd ----
            button_copyPasswd.setText("\u590d\u5236\u5bc6\u7801");
            button_copyPasswd.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
            button_copyPasswd.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    button4MouseClicked(e);
                }
            });
            popupMenu1.add(button_copyPasswd);

            //---- button_copyWeb ----
            button_copyWeb.setText("\u590d\u5236\u7f51\u7ad9");
            button_copyWeb.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
            button_copyWeb.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    button_copyWebMouseClicked(e);
                }
            });
            popupMenu1.add(button_copyWeb);

            //---- button_synDate ----
            button_synDate.setText("\u540c\u6b65\u6570\u636e");
            button_synDate.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
            button_synDate.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    button_synDateMouseClicked(e);
                }
            });
            popupMenu1.add(button_synDate);

            //---- button_delDate ----
            button_delDate.setText("\u5220\u9664\u6570\u636e");
            button_delDate.setFont(new Font("\u7b49\u7ebf", Font.PLAIN, 14));
            button_delDate.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    button_delDateMouseReleased(e);
                }
            });
            popupMenu1.add(button_delDate);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JFrame FunctionWin;
    private JLabel label_aboutus;
    private JLabel label_my;
    private JLabel label_select;
    private JDesktopPane Funselect3;
    private JPanel panel_menu3;
    private JButton button_min;
    private JButton button_close;
    private JLabel label_logo;
    private JMenuBar menuBar3;
    private JTabbedPane tabbedPane1;
    private JPanel 查询数据;
    private JPanel panel_date;
    private JScrollPane scrollPane1;
    private JTable table_copy;
    private JLabel label_synDate;
    private JLabel label_addDate;
    private JLabel label_delDate;
    private JPanel panel1;
    private JTextField textField_addaccount;
    private JTextField textField_addpasswd;
    private JTextField textField_addfromweb;
    private JLabel label_cheak;
    private JLabel label_account;
    private JLabel label_passwd;
    private JLabel label_fromweb;
    private JPanel 关于作者;
    private JLabel label_aboutLogo;
    private JLabel label_myweb;
    private JLabel label_giteeweb;
    private JPanel 个人账号;
    private JDesktopPane desktopPane_email;
    private JTextField textField_email;
    private JLabel label_nowemail;
    private JLabel label_modemail;
    private JDesktopPane desktopPane_Passwd;
    private JTextField textField_passwd;
    private JLabel label4;
    private JLabel label_modpasswd;
    private JLabel label_hasdate;
    private JLabel label_maxdate;
    private JPopupMenu popupMenu1;
    private JButton button_copyAccount;
    private JButton button_copyPasswd;
    private JButton button_copyWeb;
    private JButton button_synDate;
    private JButton button_delDate;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

}