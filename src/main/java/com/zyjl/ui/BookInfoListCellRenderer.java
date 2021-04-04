package com.zyjl.ui;

import com.zyjl.util.BookInfo;
import com.zyjl.util.NewBookInfo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class BookInfoListCellRenderer extends JLabel implements ListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        NewBookInfo bookInfo=(NewBookInfo)value;
        //设置JLable的文字
        String text="<html>"+bookInfo.getBookname()+"<br/><br/><br/>"+
                "<font color='red'>"+bookInfo.getSubject_name()+"</font>"+
                "<font color='green'>"+bookInfo.getGrade_name()+"</font>"+
                "<font color='blue'>"+bookInfo.getVersion_name()+"</font>"+"</html>";
        setText(text);
        Image img=null;
        //设置JLabel图片
        try {
            img= ImageIO.read(new URL(bookInfo.getCover()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //
        try {
            setIcon(new ImageIcon(img));
            //设置JLable的图片与文字之间的距离
            setIconTextGap(30);
        }catch (Exception ex){
            System.out.println(ex.toString());
        }
        return this;
    }
}
