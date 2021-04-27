/*
 * Created by JFormDesigner on Sun Apr 25 12:15:15 CST 2021
 */

package com.MianJu.src.GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author 1
 */

//放this下面
//            Tip_text.setSize(225,105);
//            JlabelSetText(Tip_text,"提示文本：123456789123456789123456789");
//            remindWin_Tips.setUndecorated(true);

public class RemindWin extends JDialog {

//单例，保证内存中只有一个消息框，其他消息框通过修改文本就可以

    private static RemindWin instance;


    public static RemindWin instance(Window window,String text){
        if (instance == null){
            instance = new RemindWin(window,text);
        }else{
            instance.setText(text);
            instance.remindWin_Tips.setVisible(true);
        }
        return instance;
    }
    private RemindWin(Window window,String text) {
        super(window);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents(text);
    }
    private void setText(String text){
        JlabelSetText(Tip_text,text);
        remindWin_Tips.setVisible(true);
    }

    private void yesMouseClicked(MouseEvent e) {//点击提示框确认
        remindWin_Tips.setVisible(false);
    }

    private void noMouseClicked(MouseEvent e) {//点击提示框取消
        remindWin_Tips.setVisible(false);
    }

    private void initComponents(String text) {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        remindWin_Tips = new JDialog();
        no = new JLabel();
        yes = new JLabel();
        Tip_text = new JLabel();
        Tipbeijing = new JLabel();

        //======== remindWin_Tips ========
        {
            Tip_text.setSize(225,105);
            JlabelSetText(Tip_text,text);

            remindWin_Tips.setUndecorated(true);
            remindWin_Tips.setVisible(true);
            Container remindWin_TipsContentPane = remindWin_Tips.getContentPane();
            remindWin_TipsContentPane.setLayout(null);

            //---- no ----
            no.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/\u4fe1\u606f\u6846_\u53d6\u6d88.png")));
            no.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    noMouseClicked(e);
                }
            });
            remindWin_TipsContentPane.add(no);
            no.setBounds(235, 205, 50, 30);

            //---- yes ----
            yes.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/\u4fe1\u606f\u6846_\u786e\u8ba4.png")));
            yes.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    yesMouseClicked(e);
                }
            });
            remindWin_TipsContentPane.add(yes);
            yes.setBounds(295, 205, 50, 30);

            //---- Tip_text ----
            Tip_text.setHorizontalAlignment(SwingConstants.CENTER);
            Tip_text.setFont(new Font("\u9ed1\u4f53", Font.PLAIN, 14));
            remindWin_TipsContentPane.add(Tip_text);
            Tip_text.setBounds(65, 55, 225, 105);

            //---- Tipbeijing ----
            Tipbeijing.setIcon(new ImageIcon(getClass().getResource("/com/MianJu/Test/GUI/resources/\u63d0\u793a\u6846.png")));
            remindWin_TipsContentPane.add(Tipbeijing);
            Tipbeijing.setBounds(0, -10, 360, 250);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < remindWin_TipsContentPane.getComponentCount(); i++) {
                    Rectangle bounds = remindWin_TipsContentPane.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = remindWin_TipsContentPane.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                remindWin_TipsContentPane.setMinimumSize(preferredSize);
                remindWin_TipsContentPane.setPreferredSize(preferredSize);
            }
            remindWin_Tips.pack();
            remindWin_Tips.setLocationRelativeTo(remindWin_Tips.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JDialog remindWin_Tips;
    private JLabel no;
    private JLabel yes;
    private JLabel Tip_text;
    private JLabel Tipbeijing;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    void JlabelSetText(JLabel jLabel, String longString) {
        StringBuilder builder = new StringBuilder("<html>");
        char[] chars = longString.toCharArray();
        FontMetrics fontMetrics = jLabel.getFontMetrics(jLabel.getFont());
        int start = 0;
        int len = 0;
        while (start + len < longString.length()) {
            while (true) {
                len++;
                if (start + len > longString.length())break;
                if (fontMetrics.charsWidth(chars, start, len)
                        > jLabel.getWidth()) {
                    break;
                }
            }
            builder.append(chars, start, len-1).append("<br/>");
            start = start + len - 1;
            len = 0;
        }
        builder.append(chars, start, longString.length()-start);
        builder.append("</html>");
        jLabel.setText(builder.toString());
    }
}
